package pe.com.redcups.core.repository

import android.content.Context
import pe.com.redcups.core.dao.OrderDao
import pe.com.redcups.core.dao.OrderDetailDao
import pe.com.redcups.core.model.Order
import pe.com.redcups.core.network.JuergappAPI

class OrderRepository private constructor(
    private val orderDao: OrderDao,
    private val orderDetailDao: OrderDetailDao
){
    fun getAllOrders() = orderDao.getAllOrders()

    fun getOrderDetails(orderId: Int) = orderDetailDao.getOrderDetailsByOrder(orderId)

    suspend fun fetchOrders(){
        val orders = JuergappAPI.getInstance().getResource(Array<Order>::class.java)
        for(order in orders){
            order.userId = null
            orderDao.insert(order)
        }
    }

    suspend fun performOrder(order: Order){
        JuergappAPI.getInstance().postResource(order)
    }

    companion object {
        @Volatile private var instance: OrderRepository? = null

        fun getInstance(
            orderDao: OrderDao,
            orderDetailDao: OrderDetailDao,
            context: Context) =
                instance ?: synchronized(this){
                    instance ?: OrderRepository(orderDao, orderDetailDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }
}