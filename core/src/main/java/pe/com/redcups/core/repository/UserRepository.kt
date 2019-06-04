package pe.com.redcups.core.repository

import android.content.Context
import pe.com.redcups.core.dao.UserDao
import pe.com.redcups.core.model.User
import pe.com.redcups.core.network.JuergappAPI

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers() = userDao.getAllUsers()

    fun getUser(id: String) = userDao.getUser(id)

    suspend fun insertEvent(user: User){
        userDao.insert(user)
    }

    companion object {
        @Volatile private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao, context: Context)=
            instance ?: synchronized(this){
                instance ?: UserRepository(userDao).also {
                    instance = it
                    JuergappAPI.getInstance(context)
                }
            }
    }

}