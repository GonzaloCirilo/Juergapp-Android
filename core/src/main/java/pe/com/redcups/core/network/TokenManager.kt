package pe.com.redcups.core.network

import java.util.*

class TokenManager {
    var token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NTY5MTU2MTN9.T81ispRTm4MBRqqO96zCyrZNSfmG_EFY5auFvnPahgI"
    var expirationDate: Date = Date()


    fun getAsMutableMap(): MutableMap<String,String>? = mapOf("Authorization" to "Bearer $token", "Content-Type" to "multipart/form-data").toMutableMap()

    companion object{
        private var mInstance: TokenManager = TokenManager()
        fun getInstance() = mInstance
    }
}