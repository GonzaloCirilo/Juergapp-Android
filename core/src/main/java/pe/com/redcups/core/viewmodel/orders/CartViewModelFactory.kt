package pe.com.redcups.core.viewmodel.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.OrderRepository
import pe.com.redcups.core.repository.OrderTXRepository

class CartViewModelFactory (
    private val repository: OrderTXRepository,
    private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = CartViewModel(repository, orderRepository) as T
}