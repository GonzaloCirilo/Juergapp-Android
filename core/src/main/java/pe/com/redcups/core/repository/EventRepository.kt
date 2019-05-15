package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.datasource.EventDataSource
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI


class EventRepository(private val eventDao: EventDao, context: Context): CoroutineScope by MainScope(){

    var context = context
    var allEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    fun fetchEvents() = launch{

        //allEvents = eventDao.getAllEvents()
        //some logic to see if its been fetched recently
        // Fetch from datasource
        for (event in JuergappAPI.getInstance(context).getResource(Array<Event>::class.java)){
            eventDao.insert(event)
        }
    }
}