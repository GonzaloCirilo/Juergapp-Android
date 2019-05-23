package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.repository.ProductRepository

class ProductViewModel internal constructor(productRepository: ProductRepository, productCategoryId: String): ViewModel(){

    val allProducts: LiveData<List<Product>> = productRepository.getAllProductsWithCategory(productCategoryId)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}