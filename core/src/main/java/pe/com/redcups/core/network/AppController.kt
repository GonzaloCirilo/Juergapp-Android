package pe.com.redcups.core.network

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.util.*

class AppController: Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    private fun getRequestQueue(): RequestQueue = requestQueue ?: initRequestQueue(Volley.newRequestQueue(applicationContext))

    fun <T> addRequest(request: Request<T>) = getRequestQueue().add(request)

    fun cancelRequestWithTag(tag: Objects) = requestQueue?.let { requestQueue!!.cancelAll(tag) }

    companion object{

        private var mInstance: AppController = AppController()

        private var requestQueue: RequestQueue? = null

        fun getInstance(): AppController = mInstance

        fun initRequestQueue(requestQueue: RequestQueue): RequestQueue {
            this.requestQueue = requestQueue
            return this.requestQueue!!
        }
    }
}