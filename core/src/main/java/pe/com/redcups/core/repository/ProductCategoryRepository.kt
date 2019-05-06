package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.ProductCategoryDao
import pe.com.redcups.core.model.ProductCategory

class ProductCategoryRepository(private val productCategoryDao: ProductCategoryDao){

    var allProductCategories: LiveData<List<ProductCategory>> = productCategoryDao.getAllProductCategories()

    @WorkerThread
    suspend fun insert(productCategory: ProductCategory){
        productCategoryDao.insert(productCategory)

    }


    fun fetchProductCategories(){
        allProductCategories = productCategoryDao.getAllProductCategories()
    }

}