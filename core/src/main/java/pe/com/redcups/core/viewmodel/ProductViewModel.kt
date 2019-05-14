package pe.com.redcups.core.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.repository.ProductRepository

class ProductViewModel(application: Application): AndroidViewModel(application){


    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = JuergappDatabase.getDatabase(application, viewModelScope).productDao()
        repository = ProductRepository(productDao)
        repository.fetchProducts()
        allProducts = repository.allProducts
        Log.d("size of repository all products", allProducts.value?.size.toString())
    }

    fun getProduct(productId: Int):Product {
        //get from resource
        return allProducts.value!![productId]
    }

    fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        //for debugging
        Log.d("Inserting", product.id.toString())
        Log.d("Inserting", product.name)
        repository.insert(product)
    }
    fun setProducts(products: Array<Product>){
        for (product in products){
            insert(product)
        }

    }
    fun  getProducts(context: Context): LiveData<List<Product>> {
        // Get products from repository
        repository.fetchProducts()
        return allProducts
    }

}