package pe.com.redcups.core.network

import pe.com.redcups.core.model.Event

object Constants{
    private const val baseURL = "https://wemake.pe/juergapp/api/v1"
    var eventsURL = "$baseURL/events"

    val map: Map<Class<*>,String> = mapOf(Array<Event>::class.java to eventsURL)
}