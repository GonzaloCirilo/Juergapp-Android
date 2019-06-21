package pe.com.redcups.core.viewmodel.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.OrderRepository

class OrderViewModelFactory (
    private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = OrderViewModel(orderRepository) as T
}