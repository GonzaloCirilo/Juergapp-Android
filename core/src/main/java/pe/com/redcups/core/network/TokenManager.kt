package pe.com.redcups.core.network

import java.util.*

class TokenManager {
    var token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NjU3MTk1MDh9.SoWAUL5TEGr1OMaDuDjka2YrF9T5_1f9Q37Fxd3mvxA"
    var expirationDate: Date = Date()


    fun getAsMutableMap(): MutableMap<String,String>? = mapOf("Authorization" to "Bearer $token").toMutableMap()

    companion object{
        private var mInstance: TokenManager = TokenManager()
        fun getInstance() = mInstance
    }
}