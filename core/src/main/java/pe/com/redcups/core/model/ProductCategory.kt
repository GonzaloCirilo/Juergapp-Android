package pe.com.redcups.core.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

// Add foreign keys to product
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

