package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import pe.com.redcups.core.model.Order

@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table ORDER BY id ASC")
    fun getAllOrders(): LiveData<List<Order>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}