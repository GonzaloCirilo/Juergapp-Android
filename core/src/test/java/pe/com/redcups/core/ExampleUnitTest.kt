package pe.com.redcups.core

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
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.Constants
import pe.com.redcups.core.network.GsonRequest
import pe.com.redcups.core.network.JuergappAPI
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var context: Context
    private lateinit var queue: RequestQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
    }

    @Test
    fun eventRequest(){
        val signal =  CountDownLatch(1)
        // Given
        var events: Array<Event> = emptyArray()
        // Build request
        val request = GsonRequest(
            Constants.eventsURL,
            Array<Event>::class.java,
            Request.Method.GET,
            Response.Listener {response ->
                events = response
                signal.countDown()
            },
            Response.ErrorListener {
                signal.countDown()
            })
        // When
        queue.add(request)
        signal.await()
        // Then
        assertEquals(1,events[0].id)
    }

    @Test
    fun eventRequestFacade(){
        val signal =  CountDownLatch(1)
        AppController.getInstance()
        // Given
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(context))
        var events: Array<Event> = emptyArray()
        // Make request
        JuergappAPI.getResource(
            Array<Event>::class.java,
            {
                events = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            })
        signal.await()
        // Then
        assertEquals(1,events[0].id)
    }

    @Test
    fun postEvent(){
        val signal =  CountDownLatch(1)
        AppController.getInstance()
        // Given
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(context))
        var event = Event(name = "Event Test",date = Date(), latitude = -12.0801503, longitude = -76.9543997,address = "Jr Los Helechos 140")
        // Make request
        JuergappAPI.postResource(
            Event::class.java,
            {
                Log.d("info", "insert successful")
                event = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            },
            event
        )
        signal.await()
        // Then
        assertNotEquals(0,event.id)
    }
}