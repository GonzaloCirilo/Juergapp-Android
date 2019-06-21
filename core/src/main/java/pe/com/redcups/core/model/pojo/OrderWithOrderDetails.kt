package pe.com.redcups.core.model.pojo

import androidx.room.Embedded
import androidx.room.Relation
import pe.com.redcups.core.model.tx.OrderTX
import pe.com.redcups.core.model.tx.OrderDetailTX

data class OrderWithOrderDetails (
    @Embedded var order: OrderTX,
    @Relation(parentColumn = "id", entityColumn = "orderId", entity = OrderDetailTX::class)
    var orderDetails: MutableList<OrderDetailTX>
)