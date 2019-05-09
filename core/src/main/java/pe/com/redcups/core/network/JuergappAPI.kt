package pe.com.redcups.core.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class JuergappAPI constructor(context: Context) {

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
            Constants.map[clazz] + pathVariable ?: "",
            clazz,
            method,
            Response.Listener {
                continuation.resume(it)
            },
            Response.ErrorListener {
                Log.d("Error", it.toString())
                continuation.resumeWithException(Exception(it.cause))
            },
            body
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


    suspend fun <T> deleteResurce(clazz: Class<T>, pathVariable: String? = ""): T =
        buildRequest(clazz, Request.Method.DELETE, pathVariable = pathVariable)

    suspend fun <T: Any> putResource(body: T): T =
        buildRequest((body::class.java) as Class<T>, Request.Method.PUT, body)
}