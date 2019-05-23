package pe.com.redcups.core.viewmodel.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.ProductRepository

class ProductDetailViewModelFactory(
    private val repository: ProductRepository,
    private val productId: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProductDetailViewModel(
        repository,
        productId
    ) as T
}