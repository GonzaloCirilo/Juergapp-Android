package pe.com.redcups.core

import android.content.Context
import com.android.volley.ExecutorDelivery
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import java.io.File
import java.util.concurrent.Executors

class VolleyConfig {
    companion object {
        fun newVolleyRequestQueueForTest(context: Context): RequestQueue {
            val cacheDir = File(context.cacheDir, "cache/volley")
            val network = BasicNetwork(HurlStack())
            val responseDelivery = ExecutorDelivery(Executors.newSingleThreadExecutor())
            val requestQueue = RequestQueue(DiskBasedCache(cacheDir), network, 4, responseDelivery)
            requestQueue.start()
            return requestQueue
        }
    }
}