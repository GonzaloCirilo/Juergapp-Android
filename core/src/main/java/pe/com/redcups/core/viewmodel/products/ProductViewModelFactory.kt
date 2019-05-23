package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.ProductRepository

class ProductViewModelFactory(
    private val repository: ProductRepository,
    private val productCategoryId: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProductViewModel(
        repository,
        productCategoryId
    ) as T
}