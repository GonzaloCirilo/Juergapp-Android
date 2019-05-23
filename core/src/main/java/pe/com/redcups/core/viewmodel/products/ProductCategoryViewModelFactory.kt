package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.ProductCategoryRepository

class ProductCategoryViewModelFactory (
    private val repository: ProductCategoryRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProductCategoryViewModel(
        repository
    ) as T
}