package pe.com.redcups.core.model

import androidx.room.*
import pe.com.redcups.core.helper.DateConverter
import java.util.Date

@Entity(tableName = "event_table")
@TypeConverters(DateConverter::class)
data class Event(
    @PrimaryKey
     var id: Int = 0,
     var name: String,
     var user: Int,
     var date: Date = Date(),
     var latitude: Double = 0.0,
     var longitude: Double = 0.0,
     var address: String = "",
     var picture_data: String? = null,
     var description: String = "yolo")
