package pe.com.redcups.core.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import org.jetbrains.annotations.TestOnly

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

    private fun <T> buildRequest(
        clazz: Class<T>,
        method: Int,
        response: (T) -> Unit,
        errorResponse: (VolleyError) -> Unit,
        body: T? = null
    ) {
        val request = GsonRequest(
            Constants.map[clazz] ?: "",
            clazz,
            method,
            Response.Listener {
                response(it)
            },
            Response.ErrorListener {
                errorResponse(it)
            },
            body
        )
        AppController.getInstance().addRequest(request)
    }

    fun <T> getResource(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError) -> Unit) {
        buildRequest(clazz, Request.Method.GET, response, errorResponse)
    }

    fun <T> postResource(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError) -> Unit, body: T) {
        buildRequest(clazz, Request.Method.POST, response, errorResponse, body)
    }

    fun <T> deleteResurce(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError) -> Unit) {
        buildRequest(clazz, Request.Method.DELETE, response, errorResponse)
    }

}