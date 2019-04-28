package pe.com.redcups.core.network

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.util.*

class AppController: Application() {

    private val requestQueue: RequestQueue? = null
    private lateinit var mInstance: AppController

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
    fun getInstance(): AppController = mInstance

    fun getRequestQueue(): RequestQueue = requestQueue ?: Volley.newRequestQueue(applicationContext)

    fun <T> addRequest(request: Request<T>) = getRequestQueue().add(request)

    fun cancelRequestWithTag(tag: Objects) = requestQueue?.let { requestQueue.cancelAll(tag) }
}