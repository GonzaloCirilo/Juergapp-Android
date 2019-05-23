package pe.com.redcups.core.network

import java.util.*

class TokenManager {
    var token: String = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NjU4ODc3MjR9.Ci-b0XQ2Pp-Yl9xQ7DK_W_Sb9cEIA1SsCQufyy-ubQw"
    var expirationDate: Date = Date()


    fun getAsMutableMap(): MutableMap<String,String>? = mapOf("Authorization" to "Bearer $token", "Content-Type" to "application/json").toMutableMap()

    companion object{
        private var mInstance: TokenManager = TokenManager()
        fun getInstance() = mInstance
    }
}
