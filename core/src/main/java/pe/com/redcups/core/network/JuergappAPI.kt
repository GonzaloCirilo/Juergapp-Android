package pe.com.redcups.core.network

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.android.volley.AuthFailureError
import org.json.JSONException
import android.widget.Toast
import android.R.attr.tag
import com.android.volley.VolleyLog
import com.android.volley.VolleyError
import kotlin.coroutines.CoroutineContext


class JuergappAPI constructor(context: Context) {

    var context = context
    companion object {
        @Volatile
        private var INSTANCE: JuergappAPI? = null

        fun getInstance(ctx: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: JuergappAPI(ctx).also{
                    INSTANCE = it
                }
            }
    }

    private suspend fun <T> buildRequest(
        clazz: Class<T>,
        method: Int,
        body: T? = null,
        pathVariable: String? = ""
    ): T = suspendCancellableCoroutine {continuation ->
        val request = GsonRequest(
            Constants.map[clazz] + "/" + pathVariable ?: "",
            clazz,
            method,
            Response.Listener {
                continuation.resume(it)
            },
            Response.ErrorListener {
                Log.d("Error", it.localizedMessage)
                continuation.resumeWithException(Exception(it.cause))
            },
            body
        )
        AppController.getInstance(context).addRequest(request)

        continuation.invokeOnCancellation {
            request.cancel()
        }
    }
    //this method is because DELETE doesnt return an object just a message
    private suspend fun  <T> buildDeleteRequest(
        clazz: Class<T>,
        pathVariable:String
    ): JSONObject = suspendCancellableCoroutine { continuation ->

        var request = RequestWithHeaders(
            Request.Method.DELETE,
            Constants.map[clazz] + "/" + pathVariable, null,
            Response.Listener { response ->
                continuation.resume(response)
            },
            Response.ErrorListener { error ->
                continuation.resumeWithException(Exception(error.cause))
            }
        )
        AppController.getInstance().addRequest(request)

        continuation.invokeOnCancellation {
            request.cancel()
        }

    }


    suspend fun <T> getResource(clazz: Class<T>, pathVariable: String? = ""): T =
        buildRequest(clazz, Request.Method.GET, pathVariable = pathVariable)


    suspend fun <T: Any> postResource(body: T): T =
        buildRequest((body::class.java) as Class<T>, Request.Method.POST, body)


    suspend fun  <T> deleteResource(clazz: Class<T>, pathVariable: String = ""): JSONObject =
        buildDeleteRequest(clazz,pathVariable)

    suspend fun <T: Any> putResource(body: T, pathVariable: String? = ""): T =
        buildRequest((body::class.java) as Class<T>, Request.Method.PUT, body, pathVariable = pathVariable)
}