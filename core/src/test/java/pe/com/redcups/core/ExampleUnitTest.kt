package pe.com.redcups.core

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.*
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.google.gson.reflect.TypeToken
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.Constants
import pe.com.redcups.core.network.GsonRequest
import java.io.File
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

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
        var headers = mapOf("Authorization" to "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NTY2NDc4MzV9.OS4sAkWylWgRMUT344mlwwlXT4B8lx9CeYRrACtmYiY")
        // Build request
        val request = GsonRequest(
            Constants.eventsURL,
            Array<Event>::class.java,
            headers.toMutableMap(),
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
}