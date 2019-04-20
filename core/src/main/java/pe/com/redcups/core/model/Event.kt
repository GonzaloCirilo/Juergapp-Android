package pe.com.redcups.core.model

import java.util.Date

data class Event(var id: Int = 0,
                 var name: String,
                 var user: Int,
                 var date: Date = Date(),
                 var latitude: Double = 0.0,
                 var longitude: Double = 0.0,
                 var address: String = "",
                 var description: String = "")