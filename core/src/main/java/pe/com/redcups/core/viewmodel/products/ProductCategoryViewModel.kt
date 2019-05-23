package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.repository.ProductCategoryRepository

class ProductCategoryViewModel internal constructor(productCategoryRepository: ProductCategoryRepository): ViewModel(){

    val allProductCategories: LiveData<List<ProductCategory>> = productCategoryRepository.getAllProductCategories()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}