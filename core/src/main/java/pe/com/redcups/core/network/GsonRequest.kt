package pe.com.redcups.core.network

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    method: Int,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
): Request<T>(method,url,errorListener) {

    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                    response?.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(
                gson.fromJson(json,clazz),
                HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException){
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException){
            Response.error(ParseError(e))
        }
    }
}