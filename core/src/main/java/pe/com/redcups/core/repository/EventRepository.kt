package pe.com.redcups.core.repository

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI


class EventRepository private constructor(private val eventDao: EventDao): CoroutineScope by MainScope(){

    fun getAllEvents(): LiveData<List<Event>> {
        fetchEvents{}
        return eventDao.getAllEvents()
    }

    fun refreshData(callback: () -> Unit){
        fetchEvents(callback)
    }
    fun getEvent(id: String) = eventDao.getEvent(id)

    suspend fun insertEvent(event: Event){
        JuergappAPI.getInstance().postResource(event).also {
            eventDao.insert(event)
        }

    }

    companion object {
        @Volatile private var instance: EventRepository? = null

        fun getInstance(eventDao: EventDao, context: Context)=
                instance ?: synchronized(this){
                    instance ?: EventRepository(eventDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }


    fun fetchEvents(callback: () -> Unit) = launch(Dispatchers.Main){

        //allEvents = eventDao.getAllEvents()
        //some logic to see if its been fetched recently
        // Fetch from datasource
        val events = JuergappAPI.getInstance().getResource(Array<Event>::class.java)
        for (event in events){
            eventDao.insert(event)
        }
        callback.invoke()
    }

    fun fetchEventById(id: Int) = launch {
        //event.postValue(eventDao.getEvent(id.toString()).value)

        //if (event.value == null){
            ////event.value = JuergappAPI.getInstance(context).getResource(Event::class.java,id.toString())
        //}
    }
}