package pe.com.redcups.core;

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
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
        AppController.getInstance()
        // Given
        var categories: Array<ProductCategory> = emptyArray()

        // When
        // Make Request
        runBlocking {
         JuergappAPI.getInstance(context).getResource( Array<ProductCategory>::class.java)
        }

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
