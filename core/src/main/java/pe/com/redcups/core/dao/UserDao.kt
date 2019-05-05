package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.com.redcups.core.model.User

@Dao
interface UserDao{

    @Query("SELECT * from user_table ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}

