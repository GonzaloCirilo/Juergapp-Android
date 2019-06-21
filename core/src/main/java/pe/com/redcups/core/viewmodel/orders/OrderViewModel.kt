package pe.com.redcups.core.viewmodel.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.model.Order
import pe.com.redcups.core.repository.OrderRepository

class OrderViewModel internal constructor(private val orderRepository: OrderRepository): ViewModel(){

    val allOrders: LiveData<List<Order>> = orderRepository.getAllOrders().also { refreshAllEvents() }

    fun refreshAllEvents() = viewModelScope.launch (Dispatchers.IO) {
        runBlocking {
            orderRepository.fetchOrders()
        }
    }



}