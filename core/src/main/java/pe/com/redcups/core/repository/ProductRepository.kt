package pe.com.redcups.core.repository

import android.content.Context
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.dao.ProductDao
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.JuergappAPI

class ProductRepository private constructor(private val productDao: ProductDao) {

    fun getAllProducts() = productDao.getAllProducts()

    fun getAllProductsWithCategory(productCategoryId: String) =
        productDao.getAllProductsWithCategory(productCategoryId)

    fun getProduct(id: Int) = productDao.getProduct(id)

    suspend fun insertProduct(product: Product){
        productDao.insert(product)
    }

    companion object {
        @Volatile private var instance: ProductRepository? = null

        fun getInstance(productDao: ProductDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: ProductRepository(productDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }

    suspend fun fetchProducts(){
        val products = JuergappAPI.getInstance().getResource(Array<Product>::class.java)
        for (c in products){
            runBlocking {
                val c2 = productDao.getProduct(c.id).value
                if(c2==null){
                    insertProduct(c)
                }else{
                    productDao.update(c)
                }
            }
        }
    }
}