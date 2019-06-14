package pe.com.redcups.core.repository

import android.content.Context
import pe.com.redcups.core.dao.OrderDao
import pe.com.redcups.core.model.Order
import pe.com.redcups.core.network.JuergappAPI

class OrderRepository private constructor(private val orderDao: OrderDao){

    fun getAllOrders() = orderDao.getAllOrders()

    suspend fun insertOrder(order: Order){
        JuergappAPI.getInstance().postResource(order).also {
            orderDao.insert(order)
        }
    }

    suspend fun fetchOrders() {
        for(order in JuergappAPI.getInstance().getResource(Array<Order>::class.java)){
            orderDao.insert(order)
        }
    }

    companion object {
        @Volatile private var instance: OrderRepository? = null

        fun getInstance(orderDao: OrderDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: OrderRepository(orderDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }
}