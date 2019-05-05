package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.UserDao
import pe.com.redcups.core.model.User

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

}