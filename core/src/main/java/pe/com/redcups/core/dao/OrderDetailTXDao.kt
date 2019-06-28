package pe.com.redcups.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pe.com.redcups.core.model.tx.OrderDetailTX
@Dao
interface OrderDetailTXDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(orderDetail: OrderDetailTX)

    @Query("DELETE FROM order_detail_tx_table")
    suspend fun deleteAll()
}