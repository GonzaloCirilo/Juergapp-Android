package pe.com.redcups.core.model

data class Product(
    var id: Int = 0,
    var name: String =  "name",
    var description: String =  "description",
    var volume_ml: String =  "volume_in_millileters",
    var picture: String =  "url to picture",
    var alcohol_percentage: Double  =  0.3,
    var productCategory: ProductCategory? = null
)