package pe.com.redcups.core.repository

import android.content.Context
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.dao.OrderTXDao
import pe.com.redcups.core.dao.OrderDetailTXDao
import pe.com.redcups.core.model.tx.OrderTX
import pe.com.redcups.core.model.tx.OrderDetailTX
import pe.com.redcups.core.network.JuergappAPI

class OrderTXRepository private constructor(
    private val orderDao: OrderTXDao,
    private val orderDetailDao: OrderDetailTXDao
){

    fun getAllOrders() = orderDao.getAllOrders()

    // TODO to avoid cart using id 0 in order table create a stage table with same fields
    fun getCartInstance() = orderDao.getOrderWithOrderDetails(1)

    suspend fun addToCart(orderDetail: OrderDetailTX){
        orderDetail.orderId = 1
        orderDetail.supplierId = null
        orderDetailDao.insert(orderDetail)

    }

    suspend fun emptyCart(){
        runBlocking {
            orderDetailDao.deleteAll()
            orderDao.deleteAll()
        }
    }

    suspend fun insertOrder(order: OrderTX){
        JuergappAPI.getInstance().postResource(order).also {
            orderDao.insert(order)
        }
    }
    suspend fun insertOrderInRoom(order: OrderTX){
        val o = orderDao.getOneOrder(order.id)
        if(o == null)
            orderDao.insert(order)
        else
            orderDao.update(order)
    }

    suspend fun fetchOrders() {
        for(order in JuergappAPI.getInstance().getResource(Array<OrderTX>::class.java)){
            orderDao.insert(order)
        }
    }

    companion object {
        @Volatile private var instance: OrderTXRepository? = null

        fun getInstance(orderDao: OrderTXDao, orderDetailDao: OrderDetailTXDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: OrderTXRepository(orderDao, orderDetailDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }
}