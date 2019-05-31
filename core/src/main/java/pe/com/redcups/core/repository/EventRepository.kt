package pe.com.redcups.core.repository

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI


// TODO: Remove CoroutineScope
class EventRepository private constructor(private val eventDao: EventDao): CoroutineScope by MainScope(){

    fun getAllEvents(): LiveData<List<Event>> {
        fetchEvents()
      return eventDao.getAllEvents()
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


    fun fetchEvents() = launch{

        //allEvents = eventDao.getAllEvents()
        //some logic to see if its been fetched recently
        // Fetch from datasource
        for (event in JuergappAPI.getInstance().getResource(Array<Event>::class.java)){
            eventDao.insert(event)
        }
    }

    fun fetchEventById(id: Int) = launch {
        //event.postValue(eventDao.getEvent(id.toString()).value)

        //if (event.value == null){
            ////event.value = JuergappAPI.getInstance(context).getResource(Event::class.java,id.toString())
        //}
    }
}