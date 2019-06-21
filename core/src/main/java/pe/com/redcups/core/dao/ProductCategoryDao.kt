package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import pe.com.redcups.core.model.ProductCategory

@Dao
interface ProductCategoryDao{

    @Query("SELECT * FROM product_category_table")
    fun getAllProductCategories(): LiveData<List<ProductCategory>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(productCategory: ProductCategory)

    @Query("SELECT * FROM product_category_table WHERE id LIKE :productId")
    fun getProductCategory(productId: Int): ProductCategory

    @Update
    suspend fun update(productCategory: ProductCategory)

}