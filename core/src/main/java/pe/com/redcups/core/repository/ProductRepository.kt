package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.ProductDao
import pe.com.redcups.core.model.Product

class ProductRepository private constructor(private val productDao: ProductDao){

    fun getAllProducts() = productDao.getAllProducts()
    fun getProduct(id: String) = productDao.getProduct(id)
    suspend fun insertProduct(product: Product){
        productDao.insert(product)
    }

    companion object {
        @Volatile private var instance: ProductRepository? = null

        fun getInstance(productDao: ProductDao) =
                instance ?: synchronized(this){
                    instance ?: ProductRepository(productDao).also { instance = it }
                }
    }

    /*@WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    fun fetchProducts(){
        allProducts = productDao.getAllProducts()

        //some logic to see if its been fetched recently
        // Fetch from datasource
        //JuergappAPI.getInstance(context).getResource(Array<Event>::class.java)
    }*/

}