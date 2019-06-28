package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pe.com.redcups.core.model.OrderDetail

@Dao
interface OrderDetailDao {

    @Query("SELECT * FROM order_detail_table WHERE orderId LIKE :orderId")
    fun getOrderDetailsByOrder(orderId: Int): LiveData<List<OrderDetail>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(orderDetail: OrderDetail)

    @Delete
    suspend fun delete(orderDetail: OrderDetail)

    @Query("DELETE FROM order_detail_table")
    suspend fun deleteAll()
}