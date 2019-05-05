package pe.com.redcups.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.base.CharMatcher.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pe.com.redcups.core.model.Event
import org.junit.Assert.*

@RunWith(RobolectricTestRunner::class)
class EventDaoTest {

    private lateinit var database: JuergappDatabase

    private val ID = "1"

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context,
            JuergappDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun insertAndGetTask() {
        // Insert
        database.eventDao().insert(Event(ID,  ))

        // Query an specific description
        val foundEvent: Event = database.eventDao().getEvent(ID)
        assertNotNull(foundEvent)
        assertEquals(ID, foundEvent.id)

        // Query all elements
        val allEvents: Flowable<List<Event>> = database.eventDao().getAllEvents()
        allEvents.subscribe { events ->
            run {
                events.size
                assertEquals(events.size, 1))
                val event: Event = events[0]
                assertEquals(ID, event.id)
            }
        }
    }
    @After
    fun closeDb() {
        database.close()
    }
}