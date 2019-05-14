package pe.com.redcups.core

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import kotlinx.coroutines.runBlocking
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

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun productsRequest(){
        // Given
        var products: Array<Product> = emptyArray()

        // Make request
        runBlocking {
            products = JuergappAPI.getInstance(context).getResource(Array<Product>::class.java)
        }
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
