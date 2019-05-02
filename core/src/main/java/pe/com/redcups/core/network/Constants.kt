package pe.com.redcups.core.network

import pe.com.redcups.core.model.Event
import pe.com.redcups.core.model.ProductCategory

object Constants{
    private const val baseURL = "https://wemake.pe/juergapp/api/v1"

    // Endpoitns

    // Events
    var eventsURL = "$baseURL/events"


    // Products
    var product_categoriesURL = "$baseURL/product_categories"

    // Mapping endpoints to Classes

    val map: Map<Class<*>,String> = mapOf(Array<Event>::class.java to eventsURL,
                                                Array<ProductCategory>::class.java to product_categoriesURL
                                                )
}