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

class ProductCategoryRepository(private val productCategoryDao: ProductCategoryDao, context: Context): CoroutineScope by MainScope(){

    var context = context
    var allProductCategories: LiveData<List<ProductCategory>> = productCategoryDao.getAllProductCategories()

    @WorkerThread
    suspend fun insert(productCategory: ProductCategory){
        productCategoryDao.insert(productCategory)

    }

    fun fetchProductCategories()= launch{
        allProductCategories = productCategoryDao.getAllProductCategories()

        for (pc in JuergappAPI.getInstance(context).getResource(Array<ProductCategory>::class.java)){
            productCategoryDao.insert(pc)
        }
    }

}