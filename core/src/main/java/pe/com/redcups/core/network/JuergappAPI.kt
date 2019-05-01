package pe.com.redcups.core.network

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError

class JuergappAPI {

    companion object{
        private fun <T> buildRequest(clazz: Class<T>, method: Int, response: (T) -> Unit, errorResponse: (VolleyError)-> Unit, body: T? = null){
            val request = GsonRequest(
                Constants.map[clazz] ?: "",
                clazz,
                method,
                Response.Listener{
                    response(it)
                },
                Response.ErrorListener{
                    errorResponse(it)
                },
                body
            )
            AppController.getInstance().addRequest(request)
        }

        fun <T> getResource(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError)-> Unit){
            buildRequest(clazz, Request.Method.GET, response, errorResponse)
        }

        fun <T> postResource(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError) -> Unit, body: T){
            buildRequest(clazz, Request.Method.POST, response, errorResponse,body)
        }

        fun <T> deleteResurce(clazz: Class<T>, response: (T) -> Unit, errorResponse: (VolleyError)-> Unit){
            buildRequest(clazz, Request.Method.DELETE, response, errorResponse)
        }
    }
}