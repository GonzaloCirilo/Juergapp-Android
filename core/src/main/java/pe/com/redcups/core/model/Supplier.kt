package pe.com.redcups.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplier_table")
data class Supplier(
    @PrimaryKey var id: Int,
    var ruc: String,
    var name: String,
    var picture: String
)