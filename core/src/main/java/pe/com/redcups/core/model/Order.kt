package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
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
    ]
)
data class Order (
    @PrimaryKey var id: Int = 0,
    var eventId: Int,
    var totalPrice: Double = 0.0,
    var date: Date = Date(),
    var orderHash: String = "0x0",
    var userId: Int = 0,
    @Ignore var orderLines: Array<OrderDetail> = emptyArray())

