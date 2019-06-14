package pe.com.redcups.core.repository

import android.content.Context
import pe.com.redcups.core.dao.ProductDao
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.JuergappAPI

class ProductRepository private constructor(private val productDao: ProductDao) {

    fun getAllProducts() = productDao.getAllProducts()

    fun getAllProductsWithCategory(productCategoryId: String) =
        productDao.getAllProductsWithCategory(productCategoryId)

    fun getProduct(id: String) = productDao.getProduct(id)

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
        for (c in JuergappAPI.getInstance().getResource(Array<Product>::class.java)){
            insertProduct(c)
        }
    }
}