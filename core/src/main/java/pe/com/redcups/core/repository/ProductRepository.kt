package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.ProductDao
import pe.com.redcups.core.model.Product

class ProductRepository(private val productDao: ProductDao){

    var allProducts: LiveData<List<Product>> = productDao.getAllProducts()


    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    fun fetchProducts(){
        allProducts = productDao.getAllProducts()

        //some logic to see if its been fetched recently
        // Fetch from datasource
        //JuergappAPI.getInstance(context).getResource(Array<Event>::class.java)
    }

}