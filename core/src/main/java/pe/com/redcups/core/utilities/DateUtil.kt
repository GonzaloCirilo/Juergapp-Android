package pe.com.redcups.core.utilities

import java.util.*

object DateUtil{
    fun toCustomString(calendar: Calendar): String{
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                "${(calendar.get(Calendar.MONTH)+1).toString().padStart(2,'0')}-" +
                "${calendar.get(Calendar.YEAR)}"
    }
}