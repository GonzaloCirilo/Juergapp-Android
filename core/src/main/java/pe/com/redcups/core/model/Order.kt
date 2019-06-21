package pe.com.redcups.core.model

import androidx.room.*
import java.util.*

@Entity(tableName = "order_table",
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
    ])
data class Order(
    @PrimaryKey var id: Int = 0,
    var eventId: Int? = null,
    var totalPrice: Double = 0.0,
    var date: Date = Date(),
    var orderHash: String = "",
    var userId: Int? = null,
    @Ignore var orderLines: Array<OrderDetail> = emptyArray()
)