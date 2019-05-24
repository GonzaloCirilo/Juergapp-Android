package pe.com.redcups.core.model

import androidx.room.*

@Entity(tableName = "product_table", foreignKeys = [
    ForeignKey(
        entity = ProductCategory::class,
        parentColumns = ["id"],
        childColumns = ["product_category_id"],
        onDelete= ForeignKey.CASCADE )
    ])
data class Product(
    @PrimaryKey
    var id: Int = 0,
    var name: String =  "name",
    var description: String =  "description",
    var volume_ml: String =  "volume_in_millileters",
    var picture: String =  "url to picture",
    var alcohol_percentage: Double  =  0.3,
    var product_category_id: Int = 0
)