package pe.com.redcups.core.network

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class RequestWithHeaders(
    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>?,
    errorListener: Response.ErrorListener?
) : JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return TokenManager.getInstance().getAsMutableMap()!!
    }

}