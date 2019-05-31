package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import pe.com.redcups.core.dao.UserDao
import pe.com.redcups.core.model.User

// TODO: Remove CoroutineScope
class UserRepository(private val userDao: UserDao, private val context: Context): CoroutineScope by MainScope() {

    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }
    fun getUser(id: String) = userDao.getUser(id)
    suspend fun insertEvent(user: User){
        userDao.insert(user)
    }

    companion object {
        @Volatile private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao, context: Context)=
            instance ?: synchronized(this){
                instance ?: UserRepository(userDao, context).also { instance = it }
            }
    }

}