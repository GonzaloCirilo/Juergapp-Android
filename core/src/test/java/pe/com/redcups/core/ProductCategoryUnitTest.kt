package pe.com.redcups.core;

import android.content.Context
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
import pe.com.redcups.core.network.JuergappAPI

/**
 * ProductCategory unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class ProductCategoryUnitTest {

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
     *  GET all the productCategories
     */

    /**
     * Don't use runBlocking on UI thread, use CoroutineScope instead
     */
    @Test
    fun getProductCategories(){
        val productCategories: Array<ProductCategory> =  runBlocking { JuergappAPI.getInstance(context)
            .getResource(Array<ProductCategory>::class.java)
        }
        // Then
        assertNotNull(productCategories)
        assertNotEquals(productCategories.size, -1)

        //NOTE: This test fails if server returns an empty array
        assertEquals(1, productCategories[0].id)

        // this is the default first item
        assertEquals("Cerveza", productCategories[0].name)
        assertEquals("La vieja confiable", productCategories[0].description)
    }

    /**
     *  GET  a single productCategory
     */

    @Test
    fun getProductCategory(){
        // Given
        // Get ProductCategory with id =  1
        val productCategoryId: Int = 1

        // When
        // Make request
        val productCategory: ProductCategory =  runBlocking {
            JuergappAPI.getInstance(context).getResource(ProductCategory::class.java, productCategoryId.toString())
        }

        // Then
        assertNotNull(productCategory)

        // this is the default first item
        assertEquals(1, productCategory!!.id)
        assertEquals("Cerveza", productCategory.name)
        assertEquals("La vieja confiable", productCategory.description)
    }
}
