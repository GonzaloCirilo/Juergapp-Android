package pe.com.redcups.core.network

import android.content.Context
import java.util.*
import android.content.SharedPreferences

class TokenManager(context: Context) {
    // Not implemented yet
    var expirationDate: Date = Date()


    private var mPrefs: SharedPreferences
    init {
        mPrefs = context.getSharedPreferences("JUERGAPP", Context.MODE_PRIVATE)
        setToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NjU4ODc3MjR9.Ci-b0XQ2Pp-Yl9xQ7DK_W_Sb9cEIA1SsCQufyy-ubQw")
    }
    fun getAsMutableMap(): MutableMap<String,String>? = mapOf("Authorization" to "Bearer ${getToken()}", "Content-Type" to "application/json").toMutableMap()

    companion object {
        @Volatile
        private var INSTANCE: TokenManager? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenManager(context).also{
                    INSTANCE = it
                }
            }
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun getToken(): String?{
        return mPrefs.getString("JUERGAPP_TOKEN", null)
    }
    fun setToken(token: String){
        val editor = mPrefs.edit()
        editor.putString("JUERGAPP_TOKEN", token)
        editor.apply()
    }
    fun clear() {
        mPrefs.edit().clear().commit()
    }
}
