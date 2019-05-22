package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pe.com.redcups.core.model.Product

@Dao
interface ProductDao{

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getProduct(id: String): LiveData<Product>

}