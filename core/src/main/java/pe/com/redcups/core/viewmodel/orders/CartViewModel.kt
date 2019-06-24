package pe.com.redcups.core.viewmodel.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.model.Order
import pe.com.redcups.core.model.OrderDetail
import pe.com.redcups.core.model.tx.OrderTX
import pe.com.redcups.core.model.tx.OrderDetailTX
import pe.com.redcups.core.repository.OrderRepository
import pe.com.redcups.core.repository.OrderTXRepository

class CartViewModel(private val repository: OrderTXRepository, private val orderRepository: OrderRepository) : ViewModel(){
    val cartContent = repository.getCartInstance()

    fun persist(orderDetail: OrderDetailTX)= viewModelScope.launch(Dispatchers.IO){
        repository.insertOrderInRoom(OrderTX(id = 1))
        repository.addToCart(orderDetail)
    }

/*    fun initCart()= viewModelScope.launch(Dispatchers.IO){
        repository.insertOrderInRoom(OrderTX(id = 1))
    }*/

    fun order() = viewModelScope.launch(Dispatchers.IO){
        runBlocking {
            val orderToProcess = Order()
            orderToProcess.eventId = 2
            val list = mutableListOf<OrderDetail>()
            for(od in cartContent.value!!.orderDetails){
                list.add(OrderDetail(productId = od.productId,qty = od.quantity, price = od.price, supplierId = 1, orderId = 0))
            }
            orderToProcess.orderLines = list.toTypedArray()
            orderRepository.performOrder(orderToProcess)
            repository.emptyCart()
        }
    }
}