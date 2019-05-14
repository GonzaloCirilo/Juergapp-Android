package pe.com.redcups.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.volley.RequestQueue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI

/**
 * Product unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(RobolectricTestRunner::class)
class ProductUnitTest {

    private lateinit var context: Context
    private lateinit var queue: RequestQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        //These parameters are just necessary for the test context
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
        AppController.getInstance(context).setRequestQueue(queue)
    }

    /**
     *  GET all the products
     */

    /**
     * Don't use runBlocking on UI thread, use CoroutineScope instead
     */
    @Test
    fun getProducts(){
        val products: Array<Product> =  runBlocking { JuergappAPI.getInstance(context)
            .getResource(Array<Product>::class.java)
        }
        // Then

        assertNotNull(products)
        assertNotEquals(products.size, -1)

        //NOTE: This test fails if server returns an empty array
        assertEquals(1, products[0].id)

        // this is the default first item
        assertEquals("Ron Cartavio", products[0].name)
        assertEquals("650", products[0].volume_ml)
    }

    /**
     *  GET  a single product
     */

    @Test
    fun getProduct(){
        // Given
        // Get Product with id =  1
        val productId: Int = 1

        // When
        // Make request
        val product: Product =  runBlocking {
            JuergappAPI.getInstance(context).getResource(Product::class.java, productId.toString())
        }

        // Then
        assertNotNull(product)

        // this is the default first item
        assertEquals("Ron Cartavio", product.name)
        assertEquals("650", product.volume_ml)
    }
}
