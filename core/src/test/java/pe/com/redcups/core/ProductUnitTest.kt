package pe.com.redcups.core

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.Constants
import pe.com.redcups.core.network.GsonRequest
import pe.com.redcups.core.network.JuergappAPI
import java.util.concurrent.CountDownLatch

@RunWith(RobolectricTestRunner::class)

class ProductUnitTest {
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

        var products: Array<Product> = emptyArray()
        // Build request
        val request = GsonRequest(
            Constants.productsURL,
            Array<Product>::class.java,
            Request.Method.GET,
            Response.Listener { response ->
                products = response
                signal.countDown()
            },
            Response.ErrorListener {
                signal.countDown()
            })
        // When
        queue.add(request)
        signal.await()

        // Then

        Assert.assertNotNull(products)
        Assert.assertNotEquals(products.size, -1)

        // NOTE: This test fails if server returns an empty array
        Assert.assertEquals(1, products[0].id)

        // this is the default first item
        Assert.assertEquals("Ron Cartavio", products[0].name)
        Assert.assertEquals("650", products[0].volume_ml)
    }

    @Test
    fun productsRequestFacade(){
        val signal =  CountDownLatch(1)
        AppController.getInstance()
        // Given
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(context))
        var products: Array<Product> = emptyArray()
        // Make request
        JuergappAPI.getResource(
            Array<Product>::class.java,
            {
                products = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            })
        signal.await()
        // Then

        Assert.assertNotNull(products)
        Assert.assertNotEquals(products.size, -1)

        //NOTE: This test fails if server returns an empty array
        Assert.assertEquals(1, products[0].id)

        // this is the default first item
        Assert.assertEquals("Ron Cartavio", products[0].name)
        Assert.assertEquals("650", products[0].volume_ml)
    }

}
