package pe.com.redcups.core.data

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import pe.com.redcups.core.data.model.LoggedInUser
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.TokenManager
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginDataSource {

    fun login(email: String, password: String): Result<LoggedInUser> {
        try {
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Franco Rivera")

            val url = "https://juergapp.wemake.pe/juergapp/api/v1/auth/login"

            // Post parameters
            // Form fields and values
            val params = HashMap<String,String>()
            params["email"] = "franco2@gmail.com"
            params["password"] = "123456"
            val jsonObject = JSONObject(params)

            // Volley post request with parameters
            val request = JsonObjectRequest(
                Request.Method.POST,url,jsonObject,
                Response.Listener { response ->
                    // Process the json
                    try {
                        TokenManager.getInstance().setToken(response["auth_token"] as String)
                    }catch (e:Exception){
                        Log.d("execption", e.toString())
                    }

                }, Response.ErrorListener{
                    // Error in request
                    Log.d("execption", it.toString())
                })
           // var request = StringRequest(Request.Method.POST, "https://wemake.pe/juergapp/api/v1/login",{
            AppController.getInstance().addRequest(request)

           // },
            //    {
                    //return error

             //   })
            // val user =  AppController.getInstance().addRequest()
            if(email == "franco@gmail.com" && password == "123456"){
                return Result.Success(fakeUser)
            }
            else {
                return Result.Error(IOException("Error Logging in"))
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

