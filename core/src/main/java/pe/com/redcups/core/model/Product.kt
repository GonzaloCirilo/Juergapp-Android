package pe.com.redcups.core.model

import androidx.room.*

@Entity(tableName = "product_table", foreignKeys = [
    ForeignKey(
        entity = ProductCategory::class,
        parentColumns = ["id"],
        childColumns = ["productCategoryId"],
        onDelete= ForeignKey.CASCADE )
    ],
    indices = [
        Index(value = ["productCategoryId"])
    ])
data class Product(
    @PrimaryKey
    var id: Int = 0,
    var name: String =  "name",
    var description: String =  "description",
    var volumeMl: String =  "volume_in_millileters",
    var picture: String? =  "url to picture",
    var pictureData: String? = null,
    var alcoholPercentage: Double  =  0.3,
    var productCategoryId: Int = 0
)