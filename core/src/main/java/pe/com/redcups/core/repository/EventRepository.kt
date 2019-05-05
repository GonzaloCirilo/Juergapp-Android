package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event

class EventRepository(private val eventDao: EventDao){

    var allEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    fun fetchEvents(){
        allEvents = eventDao.getAllEvents()

    }
        // AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(view.context))

        //JuergappAPI.getResource(
        //    Array<Event>::class.java,
        //   {
        //        viewModel.setEvents(it)
        //    },
        //   {
        //      Log.d("error", it.toString())
        // }
        // )
}