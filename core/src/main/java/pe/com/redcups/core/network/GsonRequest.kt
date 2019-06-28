package pe.com.redcups.core.network

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.Base64.getEncoder
import org.json.JSONObject
import pe.com.redcups.core.model.Event
import java.lang.reflect.Type


class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    method: Int,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener,
    var dataIn: T? = null,
    var fileIn: Map<String, ByteArray>? = null
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
        builder
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }


    private val gson = builder.create()

    override fun getHeaders(): MutableMap<String, String> {
        var aux_headers = TokenManager.getInstance().getAsMutableMap() ?: super.getHeaders()
        if(fileIn == null){
            aux_headers.set("Content-Type", "application/json")
        }
        return aux_headers
    }

    override fun getBodyContentType(): String { return "multipart/form-data;boundary=$boundary" }

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

    fun formDataForFiles(dos: DataOutputStream, file: Map.Entry<String, ByteArray>){
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\""+ file.key+"\";filename=\"" + "upload_file.png" + "\"" + lineEnd);
        dos.writeBytes("Content-Type: image/jpeg"  + lineEnd);
        dos.writeBytes(lineEnd);

        var fileInputStream = ByteArrayInputStream(file.value)
        var bytesAvailable = fileInputStream.available()

        var maxBufferSize = 1024 * 1024
        var bufferSize = Math.min(bytesAvailable, maxBufferSize)
        var buffer = ByteArray(bufferSize)

        var bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        dos.writeBytes(lineEnd);
    }
    override fun getBody(): ByteArray {
        var bos =  ByteArrayOutputStream()
        var dos = DataOutputStream(bos)
        try {
            if  (fileIn != null) {
                //iterate through Gson fields

                var ob = JSONObject(gson.toJson(dataIn!!))

                for (i in ob.keys()) {
                    dos.run {
                        writeBytes(twoHyphens + boundary + lineEnd)
                        writeBytes("Content-Disposition: form-data; name=\"" + i.toString() + "\"" + lineEnd)
                        writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                        writeBytes(lineEnd);
                    };
                    // convert String for utf 8
                    val str = ob[i].toString()
                    val bait = str.toByteArray(Charsets.UTF_8)
                    dos.run {
                        write(bait)
                        writeBytes(lineEnd)
                    }
                }
                fileIn?.also {
                    fileIn!!.entries.forEach {
                        formDataForFiles(dos, it)
                    }
                }
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                return bos.toByteArray()
            }
            return gson.toJson(dataIn!!).toByteArray()
        }
        catch (e: Exception){
            //handle exception
            return bos.toByteArray()
        }
    }
    inner class DataPart ( var fileName: String? = null, var content: ByteArray? = null, var type: String? = null)
}
