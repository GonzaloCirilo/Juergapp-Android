package pe.com.redcups.core.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.ProductDao
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.JuergappAPI

class ProductRepository private constructor(private val productDao: ProductDao, private val context: Context): CoroutineScope by MainScope(){

    fun getAllProducts(): LiveData<List<Product>> {
        fetchProducts()
      return productDao.getAllProducts()
    }
    fun getAllProductsWithCategory(productCategoryId: String): LiveData<List<Product>>{
        fetchProducts()
        Log.d("Category ID", productCategoryId)
        return productDao.getAllProductsWithCategory(productCategoryId)
    }

    fun getProduct(id: String) = productDao.getProduct(id)

    suspend fun insertProduct(product: Product){
        productDao.insert(product)
    }

    companion object {
        @Volatile private var instance: ProductRepository? = null

        fun getInstance(productDao: ProductDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: ProductRepository(productDao, context).also { instance = it }
                }
    }


    fun fetchProducts() = launch{
        //allProducts = productDao.getAllProducts()

        //some logic to see if its been fetched recently
        // Fetch from datasource
        for (c in JuergappAPI.getInstance(context).getResource(Array<Product>::class.java)){
            insertProduct(c)
        }
    }

}