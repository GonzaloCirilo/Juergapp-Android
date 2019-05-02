package pe.com.redcups.core.model

import java.util.Date

data class Game ( var id: Int,
                  var name: String,
                  var description: String = "",
                  var created_at: Date = Date(),
                  var updated_at: Date = Date())