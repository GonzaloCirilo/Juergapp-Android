package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import pe.com.redcups.core.model.Product

@Dao
interface ProductDao{

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE productCategoryId = :product_category_id")
    fun getAllProductsWithCategory(product_category_id: String): LiveData<List<Product>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getProduct(id: Int): LiveData<Product>

    @Update
    fun update(product: Product)

}