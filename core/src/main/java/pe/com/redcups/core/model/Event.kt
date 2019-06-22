package pe.com.redcups.core.model

import androidx.room.*
import pe.com.redcups.core.helper.DateConverter
import java.util.Date

@Entity(tableName = "event_table")
@TypeConverters(DateConverter::class)
data class Event(
    @PrimaryKey(autoGenerate = true)
     var id: Int? = null,
     var name: String,
     var user: Int = 0,
     var date: Date = Date(),
     var latitude: Double = 0.0,
     var longitude: Double = 0.0,
     var address: String = "jr",
     var pictureData: String? = null,
     var description: String = "yolo")
