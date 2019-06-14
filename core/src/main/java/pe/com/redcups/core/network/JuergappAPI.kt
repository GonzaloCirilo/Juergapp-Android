package pe.com.redcups.core.network

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.consumesAll
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class JuergappAPI  {

    private var hasInternetAccess: Boolean = true

    companion object {
        @Volatile
        private var INSTANCE: JuergappAPI? = null

        fun getInstance(ctx: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: JuergappAPI().also{
                    INSTANCE = it
                    INSTANCE!!.hasInternetAccess = Connection.hasInternetAccess(ctx)
                    AppController.getInstance(ctx)
                    TokenManager.getInstance(ctx)
                }
            }

        fun getInstance() = INSTANCE!!
    }

    @UseExperimental(InternalCoroutinesApi::class)
    private suspend fun <T> buildRequest(
        clazz: Class<T>,
        method: Int,
        body: T? = null,
        pathVariable: String? = "",
        errorCallback: () -> Unit = {}
    ): T = suspendCancellableCoroutine {continuation ->
        val request = GsonRequest(
            Constants.map[clazz] + "/" + pathVariable,
            clazz,
            method,
            Response.Listener {
                continuation.resume(it)
            },
            Response.ErrorListener {
                errorCallback.invoke()
                continuation.tryResumeWithException(Exception(it))
            },
            body
        )
        if(hasInternetAccess)
            AppController.getInstance().addRequest(request)

        continuation.invokeOnCancellation {
            request.cancel()
        }
    }
    //this method is because DELETE doesnt return an object just a message
    private suspend fun  <T> buildDeleteRequest(
        clazz: Class<T>,
        pathVariable:String
    ): JSONObject = suspendCancellableCoroutine { continuation ->

        val request = RequestWithHeaders(
            Request.Method.DELETE,
            Constants.map[clazz] + "/" + pathVariable, null,
            Response.Listener { response ->
                continuation.resume(response)
            },
            Response.ErrorListener { error ->
                continuation.resumeWithException(Exception(error.cause))
            }
        )
        if(hasInternetAccess)
            AppController.getInstance().addRequest(request)

        continuation.invokeOnCancellation {
            request.cancel()
        }

    }

    suspend fun getImage(url: String, height: Int, width: Int): Bitmap =
        suspendCancellableCoroutine { continuation ->
            val imageRequest = ImageRequest(url, Response.Listener<Bitmap> { image ->
                    continuation.resume(image)
                },
                height,
                width,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                Response.ErrorListener{ error ->
                    Log.d("VolleyError", error.toString())
                })

            if(hasInternetAccess)
                AppController.getInstance().addRequest(imageRequest)

            continuation.invokeOnCancellation {
                imageRequest.cancel()
            }
        }

    suspend fun <T> getResource(clazz: Class<T>, pathVariable: String? = "", errorCallback: () -> Unit = {}): T =
        buildRequest(clazz, Request.Method.GET, pathVariable = pathVariable, errorCallback = errorCallback)


    suspend fun <T: Any> postResource(body: T, errorCallback: () -> Unit = {}): T =
        buildRequest((body::class.java) as Class<T>, Request.Method.POST, body, errorCallback = errorCallback)


    suspend fun  <T> deleteResource(clazz: Class<T>, pathVariable: String = ""): JSONObject =
        buildDeleteRequest(clazz,pathVariable)

    suspend fun <T: Any> putResource(body: T, pathVariable: String? = "", errorCallback: () -> Unit = {}): T =
        buildRequest((body::class.java) as Class<T>, Request.Method.PUT, body, pathVariable = pathVariable, errorCallback = errorCallback)
}