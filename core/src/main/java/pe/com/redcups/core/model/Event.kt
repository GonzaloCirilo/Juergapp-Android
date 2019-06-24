package pe.com.redcups.core.model
import android.graphics.Bitmap
import androidx.room.*
import pe.com.redcups.core.helper.DateConverter
import java.io.Serializable
import java.util.Date

@Entity(tableName = "event_table")
@TypeConverters(DateConverter::class)
data class Event(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String = "",
    var user: Int = 0,
    var date: Date = Date(),
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var address: String = "JR Los Helechos",
    var pictureData: String? = null,
    var description: String = "El Mejor evento del año",
    @Transient @Ignore var picture: ByteArray? = null
)
