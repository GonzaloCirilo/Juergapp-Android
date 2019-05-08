package pe.com.redcups.core.network

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import org.jetbrains.annotations.TestOnly
import java.util.*

class AppController constructor(context: Context) {

    private lateinit var requestQueue: RequestQueue

    companion object {
        @Volatile
        private var INSTANCE: AppController? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: AppController(context).also {
                        INSTANCE = it
                        INSTANCE!!.initRequestQueue(context)

                    }
                }
        fun getInstance() = INSTANCE!!
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    fun <T> addRequest(request: Request<T>) = requestQueue.add(request)

    fun cancelRequestWithTag(tag: Objects) = requestQueue?.let { requestQueue!!.cancelAll(tag) }

    private fun initRequestQueue(context: Context){
        requestQueue= Volley.newRequestQueue(context)
    }

    @TestOnly
    fun setRequestQueue(requestQueue: RequestQueue){
        INSTANCE!!.requestQueue = requestQueue
    }
}