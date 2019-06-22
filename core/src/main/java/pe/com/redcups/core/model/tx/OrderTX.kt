package pe.com.redcups.core.model.tx

import androidx.room.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.model.User
import java.util.*

@Entity(tableName = "order_tx_table",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["eventId"]),
        Index(value = ["userId"])
    ]
)
data class OrderTX (
    @PrimaryKey var id: Int = 0,
    var eventId: Int? = null,
    var totalPrice: Double = 0.0,
    var date: Date = Date(),
    var orderHash: String = "0x0",
    var userId: Int? = null,
    // Gson properties
    @Ignore var orderLines: Array<OrderDetailTX> = emptyArray())

