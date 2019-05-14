package pe.com.redcups.core.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI


class EventRepository(private val eventDao: EventDao){

    var allEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    fun fetchEvents(){
        allEvents = eventDao.getAllEvents()

        //some logic to see if its been fetched recently
        //JuergappAPI.getInstance(context).getResource(Array<Event>::class.java)

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