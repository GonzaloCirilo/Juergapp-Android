package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.repository.ProductRepository

class ProductDetailViewModel internal constructor(productRepository: ProductRepository, productId: String): ViewModel(){

    val product: LiveData<Product> = productRepository.getProduct(productId)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}