package pe.com.redcups.core.model.tx

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.model.Supplier

@Entity(
    tableName = "order_detail_tx_table",
    foreignKeys = [
        ForeignKey(
            entity = OrderTX::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplierId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["supplierId"]),
        Index(value = ["orderId"])
    ]
)
data class OrderDetailTX (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var productId: Int? = null,
    var price: Float = 0.0f,
    var quantity: Int = 0,
    var supplierId: Int? = null,
    var orderId: Int
)