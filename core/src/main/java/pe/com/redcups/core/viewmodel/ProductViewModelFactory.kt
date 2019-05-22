package pe.com.redcups.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.ProductRepository

class ProductViewModelFactory(
    private val repository: ProductRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProductViewModel(repository) as T
}