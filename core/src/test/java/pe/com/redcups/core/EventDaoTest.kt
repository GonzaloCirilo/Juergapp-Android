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
        //TODO: Implement DAOtest
    }
    @After
    fun closeDb() {
        database.close()
    }
}