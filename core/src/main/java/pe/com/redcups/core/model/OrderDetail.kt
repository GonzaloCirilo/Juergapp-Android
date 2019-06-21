package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_detail_table",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = Supplier::class,
        parentColumns = ["id"],
        childColumns = ["supplierId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["supplierId"]),
        Index(value = ["orderId"])
    ]
)
data class OrderDetail (
    @PrimaryKey var id: Int = 0,
    var productId: Int? = null,
    var price: Float = 0.0f,
    var qty: Int = 0,
    var supplierId: Int? = null,
    var orderId: Int
)