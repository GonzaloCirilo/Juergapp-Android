package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.com.redcups.core.model.Order
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table")
    fun getAllOrders(): LiveData<List<Order>>


    @Insert(onConflict = REPLACE)
    suspend fun insert(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}