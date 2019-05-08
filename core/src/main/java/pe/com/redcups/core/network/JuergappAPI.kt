package pe.com.redcups.core.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class JuergappAPI constructor(flag: Context) {

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

    suspend fun <T> getResource(clazz: Class<T>, pathVariable: String?): T =
        buildRequest(clazz, Request.Method.GET, pathVariable = pathVariable)


    suspend fun <T> postResource(clazz: Class<T>, body: T): T =
        buildRequest(clazz, Request.Method.POST, body)


    suspend fun <T> deleteResurce(clazz: Class<T>, pathVariable: String?): T =
        buildRequest(clazz, Request.Method.DELETE, pathVariable = pathVariable)

}