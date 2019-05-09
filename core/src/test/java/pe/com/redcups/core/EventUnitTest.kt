package pe.com.redcups.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.volley.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class EventUnitTest {

    private lateinit var context: Context
    private lateinit var queue: RequestQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
        AppController.getInstance(context).setRequestQueue(queue)

    }

    /**
     * Don't use runBlocking on UI thread, use CoroutineScope instead
     */

    @Test
    fun getEvent(){
        val events: Array<Event> =  runBlocking { JuergappAPI.getInstance(context)
            .getResource(Array<Event>::class.java)
        }
        // Then
        assertEquals(1,events[0].id)
    }

    @Test
    fun postEvent(){
        var event = Event(name = "Event Test",date = Date(), latitude = -12.0801503, longitude = -76.9543997,address = "Jr Los Helechos 140")
        // Make request
        event = runBlocking {
            JuergappAPI.getInstance(context).postResource(event)
        }
        // Then
        assertNotEquals(0,event.id)
    }
}