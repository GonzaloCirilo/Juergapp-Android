package pe.com.redcups.core.network

import pe.com.redcups.core.model.*
import pe.com.redcups.core.model.tx.OrderTX

object Constants{
    private const val baseURL = "https://juergapp.wemake.pe/juergapp/api/v1"

    // EndPoints

    // Events
    var eventsURL = "$baseURL/events"

    // Products

    // List of Categories
    var product_categoriesURL = "$baseURL/product_categories"

    // Get one Product Category of products
    var product_categoryURL = "$baseURL/product_categories"

    // List of products
    var productsURL = "$baseURL/products"
    //var productsURL = "$baseURL/product_categories"

    // List of Games
    var gamesURL = "$baseURL/games"

    var orderURL = "$baseURL/orders"

    // Mapping endpoints to Classes

    val map: Map<Class<*>,String> = mapOf(

        Array<Event>::class.java to eventsURL,
        Event::class.java to eventsURL,

        Array<ProductCategory>::class.java to product_categoriesURL,
        ProductCategory::class.java to product_categoryURL,

        Array<Product>::class.java to productsURL,
        Product::class.java to productsURL,

        Array<Game>::class.java to gamesURL,
        Game::class.java to gamesURL,

        Array<Order>::class.java to orderURL,
        Order::class.java to orderURL

    )
}
