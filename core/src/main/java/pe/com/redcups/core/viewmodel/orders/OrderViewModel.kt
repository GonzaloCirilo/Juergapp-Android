package pe.com.redcups.core.viewmodel.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.com.redcups.core.model.Order
import pe.com.redcups.core.repository.OrderRepository

class OrderViewModel internal constructor(orderRepository: OrderRepository): ViewModel(){

    val allOrders: LiveData<List<Order>> = orderRepository.getAllOrders()


}