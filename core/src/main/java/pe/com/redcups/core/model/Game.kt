package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pe.com.redcups.core.helper.DateConverter
import java.util.Date

@Entity(tableName = "game_table")
@TypeConverters(DateConverter::class)
data class Game (
    @PrimaryKey
    var id: Int,
    var name: String,
    var description: String = "",
    var picture_data: String? = null,
    var created_at: Date = Date(),
    var updated_at: Date = Date())