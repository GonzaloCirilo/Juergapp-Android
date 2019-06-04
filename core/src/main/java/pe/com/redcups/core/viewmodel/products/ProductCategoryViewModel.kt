package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import pe.com.redcups.core.repository.ProductCategoryRepository

class ProductCategoryViewModel internal constructor(private val productCategoryRepository: ProductCategoryRepository): ViewModel(){

    val allProductCategories = productCategoryRepository.getAllProductCategories().also { refreshProductCategories() }

    fun refreshProductCategories() = viewModelScope.launch(Dispatchers.IO) {
        productCategoryRepository.fetchProductCategories()
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}