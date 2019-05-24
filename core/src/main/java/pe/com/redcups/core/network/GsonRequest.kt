package pe.com.redcups.core.network

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val context: Context,
    method: Int,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener,
    var dataIn: T? = null
): Request<T>(method,url,errorListener) {

    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = TokenManager.getInstance(context).getAsMutableMap() ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException){
            Response.error(ParseError(e))
        }
    }

    override fun getBody(): ByteArray = gson.toJson(dataIn!!).toByteArray()

}