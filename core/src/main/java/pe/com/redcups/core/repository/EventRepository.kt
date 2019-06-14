package pe.com.redcups.core.repository

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI

class EventRepository private constructor(private val eventDao: EventDao){

    fun getAllEvents(): LiveData<List<Event>> {
        return eventDao.getAllEvents()
    }

    suspend fun refreshData(){
        fetchEvents{}
    }

    fun getEvent(id: String) = eventDao.getEvent(id)

    suspend fun insertEvent(event: Event){
        JuergappAPI.getInstance().postResource(event).also {
            withContext(Dispatchers.IO){
                eventDao.insert(event)
            }
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

    suspend fun fetchEvents(callback: () -> Unit) {
        val events = JuergappAPI.getInstance().getResource(Array<Event>::class.java,errorCallback = callback)
        for (event in events){
            withContext(Dispatchers.IO){
                eventDao.insert(event)
            }
        }
        callback.invoke()
    }

}