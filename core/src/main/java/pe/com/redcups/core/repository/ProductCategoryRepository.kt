package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.ProductCategoryDao
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.JuergappAPI


// TODO: Remove CoroutineScope
class ProductCategoryRepository private constructor(private val productCategoryDao: ProductCategoryDao, private val context: Context): CoroutineScope by MainScope(){

    fun getAllProductCategories(): LiveData<List<ProductCategory>>{
        fetchProductCategories()
        return productCategoryDao.getAllProductCategories()
    }

    companion object {
        @Volatile private var instance: ProductCategoryRepository? = null

        fun getInstance(productCategoryDao: ProductCategoryDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: ProductCategoryRepository(productCategoryDao, context).also { instance = it }
                }
    }

    @WorkerThread
    suspend fun insert(productCategory: ProductCategory){
        productCategoryDao.insert(productCategory)

    }

    fun fetchProductCategories()= launch{
        //allProductCategories = productCategoryDao.getAllProductCategories()

        for (pc in JuergappAPI.getInstance(context).getResource(Array<ProductCategory>::class.java)){
            productCategoryDao.insert(pc)
        }
    }
}