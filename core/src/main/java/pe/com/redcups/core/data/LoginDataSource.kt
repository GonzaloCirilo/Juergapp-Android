package pe.com.redcups.core.data

import pe.com.redcups.core.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(email: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Franco Rivera")
           // var request = StringRequest(Request.Method.POST, "https://wemake.pe/juergapp/api/v1/login",{

                //return user
           // },
            //    {
                    //return error

             //   })
            // val user =  AppController.getInstance().addRequest()

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

