package pe.com.redcups.core.network

import android.content.Context
import android.net.ConnectivityManager

class Connection {
    companion object{
        fun hasInternetAccess(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = manager.activeNetworkInfo

            return !(netInfo == null || !netInfo.isConnected)
        }
    }
}