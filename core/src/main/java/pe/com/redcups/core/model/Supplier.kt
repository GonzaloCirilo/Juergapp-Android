package pe.com.redcups.core.model

import androidx.room.PrimaryKey

data class Supplier(
    @PrimaryKey var id: Int,
    var ruc: String,
    var name: String,
    var picture: String
)