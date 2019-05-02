package pe.com.redcups.core;

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.Constants
import pe.com.redcups.core.network.GsonRequest
import pe.com.redcups.core.network.JuergappAPI
import java.util.concurrent.CountDownLatch

@RunWith(RobolectricTestRunner::class)

class ProductCategoryUnitTest {
    private lateinit var context: Context
    private lateinit var queue: RequestQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
    }

    @Test
    fun productsRequest(){
        val signal =  CountDownLatch(1)
        // Given

        var categories: Array<ProductCategory> = emptyArray()
        // Build request
        val request = GsonRequest(
                Constants.product_categoriesURL,
                Array<ProductCategory>::class.java,
                Request.Method.GET,
                Response.Listener {response ->
                categories = response
            signal.countDown()
        },
        Response.ErrorListener {
            signal.countDown()
        })
        // When
        queue.add(request)
        signal.await()

        // Then

        assertNotNull(categories)
        assertNotEquals(categories.size, -1)

        // NOTE: This test fails if server returns an empty array
        assertEquals(1, categories[0].id)

        // this is the default first item
        assertEquals("Cerveza", categories[0].name)
        assertEquals("La vieja confiable", categories[0].name)
    }

    @Test
    fun productsRequestFacade(){
        val signal =  CountDownLatch(1)
        AppController.getInstance()
        // Given
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(context))
        var categories: Array<ProductCategory> = emptyArray()
        // Make request
        JuergappAPI.getResource(
                Array<ProductCategory>::class.java,
                {
                        categories = it
                        signal.countDown()
                },
                {
                        Log.d("error", it.toString())
                        signal.countDown()
                })
        signal.await()
        // Then

        assertNotNull(categories)
        assertNotEquals(categories.size, -1)

        //NOTE: This test fails if server returns an empty array
        assertEquals(1, categories[0].id)

        // this is the default first item
        assertEquals("Cerveza", categories[0].name)
        assertEquals("La vieja confiable", categories[0].name)
    }

}
