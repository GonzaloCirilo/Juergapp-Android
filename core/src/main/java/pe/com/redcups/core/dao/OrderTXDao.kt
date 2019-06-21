package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import pe.com.redcups.core.model.tx.OrderTX
import pe.com.redcups.core.model.pojo.OrderWithOrderDetails

@Dao
interface OrderTXDao {

    @Query("SELECT * FROM order_tx_table WHERE id NOT LIKE 0 ORDER BY date ASC")
    fun getAllOrders(): LiveData<List<OrderTX>>

    // Get singleton order
    @Transaction
    @Query("SELECT * FROM order_tx_table WHERE id LIKE :orderId ")
    fun getOrderWithOrderDetails(orderId: Int): LiveData<OrderWithOrderDetails>

    @Insert(onConflict = REPLACE)
    suspend fun insert(order: OrderTX)

    @Delete
    suspend fun delete(order: OrderTX)

    @Query("DELETE FROM order_tx_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM order_tx_table WHERE id LIKE :id")
    fun getOneOrder(id: Int): OrderTX?

    @Update
    fun update(order: OrderTX)
}