package pe.com.redcups.core.network

import com.android.volley.Request
import com.android.volley.Response

class JuergappAPI {
    companion object{
        fun <T> getResource(clazz: Class<T>,response: Response.Listener<T>, errorResponse: Response.ErrorListener){
            val request = GsonRequest(
                Constants.map[clazz] ?: "",
                clazz,
                Request.Method.GET,
                response,
                errorResponse
            )
            AppController.getInstance().addRequest(request)
        }
    }
}