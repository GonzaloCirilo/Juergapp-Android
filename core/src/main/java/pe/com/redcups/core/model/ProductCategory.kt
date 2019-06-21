package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "product_category_table")
data class ProductCategory (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "Nombre de la Categoría",
    var description: String = "Breve descripción de Categoría",
    var picture_data: String? = null,
    @Ignore
    var picture: String = "URL de la imagen"
)

