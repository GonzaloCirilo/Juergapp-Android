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
        setToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1Njk1OTc4NDF9.Y3fZVQbXbfMavQid-ddIzocVOxJGHDcR2rYWHs17db8")
    }
    fun getAsMutableMap(): MutableMap<String,String>? = mapOf("Authorization" to "Bearer ${getToken()}" ).toMutableMap()

    companion object {
        @Volatile
        private var INSTANCE: TokenManager? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenManager(context).also{
                    INSTANCE = it
                }
            }

        fun getInstance(): TokenManager = INSTANCE!!
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
