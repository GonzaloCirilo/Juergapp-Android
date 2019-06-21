package pe.com.redcups.core.network

import android.graphics.Bitmap
import android.util.Base64
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.Base64.getEncoder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    method: Int,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener,
    var dataIn: T? = null
): Request<T>(method,url,errorListener) {

    private val twoHyphens = "--"
    private val lineEnd = "\r\n"
    private val boundary = "apiclient-" + System.currentTimeMillis()

    var builder = GsonBuilder()
    // Adapter to convert byte[] in a Java Object to a String in Json for UI to display image properly
    init{
        builder.registerTypeAdapter(Bitmap::class.java, object : JsonSerializer<Bitmap> {
            override fun serialize(src: Bitmap, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
                var byteArrayOutputStream =  ByteArrayOutputStream()
                src.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                return JsonPrimitive(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT))
            }
        })
    }


    private val gson = builder.create()

    override fun getHeaders(): MutableMap<String, String> = TokenManager.getInstance().getAsMutableMap() ?: super.getHeaders()

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$boundary"
    }

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

    override fun getBody(): ByteArray {
        var bos =  ByteArrayOutputStream()
        var dos = DataOutputStream(bos)
        try {
            dos.writeBytes(gson.toJson(dataIn!!))
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
//                     + "ic_action_file_attachment_light.png" + "\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//
//            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bitmapData)
//            bytesAvailable = fileInputStream.available();
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
////
//            // read file and write it into form...
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
//            while (bytesRead > 0) {
//                dos.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            }
////
//            // send multipart form data necesssary after file data...
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
            return bos.toByteArray()
        }
        catch (e: Exception){
            //handle edception
            return bos.toByteArray()
        }
    }
    inner class DataPart ( var fileName: String? = null, var content: ByteArray? = null, var type: String? = null)
}