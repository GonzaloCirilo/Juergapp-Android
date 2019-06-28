package pe.com.redcups.core.model

import androidx.room.*

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "Nombre de la Categoría",
    var bio: String = "Breve descripción de Categoría",
    var picture: String = "URL de la imagen",
    var pictureData: String? = null
)


