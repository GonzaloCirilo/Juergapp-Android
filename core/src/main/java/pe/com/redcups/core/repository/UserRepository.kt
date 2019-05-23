package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.UserDao
import pe.com.redcups.core.model.User

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers() = userDao.getAllUsers()
    fun getUser(id: String) = userDao.getUser(id)
    suspend fun insertEvent(user: User){
        userDao.insert(user)
    }

    companion object {
        @Volatile private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao)=
            instance ?: synchronized(this){
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }

}