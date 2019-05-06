package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "product_category_table")
data class ProductCategory (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "Nombre de la Categoría",
    @Ignore
    var picture: String = "URL de la imagen",
    var description: String = "Breve descripción de Categoría"
)
