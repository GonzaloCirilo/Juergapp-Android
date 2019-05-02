package pe.com.redcups.core.network

import pe.com.redcups.core.model.Event
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.model.ProductCategory

object Constants{
    private const val baseURL = "https://wemake.pe/juergapp/api/v1"

    // Endpoitns

    // Events
    var eventsURL = "$baseURL/events"


    // Products

    // List of Categories
    var product_categoriesURL = "$baseURL/product_categories"

    // List of products
    var productsURL = "$baseURL/products"

    // Mapping endpoints to Classes

    val map: Map<Class<*>,String> = mapOf(
        Array<Event>::class.java to eventsURL,
        Array<ProductCategory>::class.java to product_categoriesURL,
        Array<Product>::class.java to productsURL
    )
}