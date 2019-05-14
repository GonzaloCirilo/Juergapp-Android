package pe.com.redcups.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.volley.RequestQueue
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
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
 * Event unit test, which will execute on the development machine (host).
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

        //These parameters are just necessary for the test context
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
        AppController.getInstance(context).setRequestQueue(queue)
    }


    /**
     *  GET all the events
     */

    /**
     * Don't use runBlocking on UI thread, use CoroutineScope instead
     */
    @Test
    fun getEvents(){
        val events: Array<Event> =  runBlocking { JuergappAPI.getInstance(context)
            .getResource(Array<Event>::class.java)
        }
        // Then

        // check its not null
        assertNotNull(events)

        // check arbitrary first element
        assertEquals(1,events[0].id)
        assertEquals( "PUT Event Test", events[0].name)
    }

    /**
     *  GET  a single event
     */

    @Test
    fun getEvent(){

        // Given
        val eventId: Int = 1

        //When
        val event: Event =  runBlocking {
            JuergappAPI.getInstance(context).getResource(Event::class.java, eventId.toString())
        }
        // Then
        assertEquals(1,event.id)
    }

    /**
     *  PUT event
     */

    @Test
    fun putEvent(){

        // Given
        val eventId: Int = 1

        var event = Event(name = "PUT Event Test",
            date = Date(),
            latitude = -12.0801503,
            longitude = 0.123456,
            address = "Jr Los Helechos 140",
            user = 10)

        // When
        // Make request
        event = runBlocking {
            JuergappAPI.getInstance(context).putResource(event, eventId.toString())
        }

        // Then
        assertNotEquals(0,event.id)

        assertEquals("PUT Event Test", event.name)
        assertEquals( (0.123456).toString(), event.longitude.toString())
    }

    /**
     *  POST and Delete /event
     */

    @Test
    fun postAndDeleteEvent(){
        var event = Event(name = "Event Test",
            date = Date(),
            latitude = -12.0801503,
            longitude = -76.9543997,
            address = "Jr Los Helechos 140",
            user = 10)

        // Make request
        event = runBlocking {
            JuergappAPI.getInstance(context).postResource(event)
        }
        // Then
        assertNotEquals(0,event.id)


        // Delete event created

        var message: JSONObject = JSONObject()

        // When
        // Make request
        runBlocking {
            message = JuergappAPI.getInstance(context).deleteResource(Event::class.java, event.id.toString())
        }
        // Then
        assertEquals("OK", message["status"])
        assertEquals("event deleted successfully", message["message"])

    }

}