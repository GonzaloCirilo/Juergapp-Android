package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import pe.com.redcups.core.dao.ProductCategoryDao
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.JuergappAPI

class ProductCategoryRepository private constructor(private val productCategoryDao: ProductCategoryDao) {

    fun getAllProductCategories() = productCategoryDao.getAllProductCategories()

    companion object {
        @Volatile private var instance: ProductCategoryRepository? = null

        fun getInstance(productCategoryDao: ProductCategoryDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: ProductCategoryRepository(productCategoryDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }

    @WorkerThread
    suspend fun insert(productCategory: ProductCategory){
        productCategoryDao.insert(productCategory)

    }

    suspend fun fetchProductCategories() {
        for (pc in JuergappAPI.getInstance().getResource(Array<ProductCategory>::class.java)){
            val pc2 = productCategoryDao.getProductCategory(pc.id)
            if(pc2==null){
                productCategoryDao.insert(pc)
            }else{
                productCategoryDao.update(pc)
            }
        }
    }
}