package pe.com.redcups.core.repository

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.EventDao
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI


class EventRepository private constructor(private val eventDao: EventDao){

    fun getAllEvents() = eventDao.getAllEvents()
    fun getEvent(id: String) = eventDao.getEvent(id)
    suspend fun insertEvent(event: Event){
        eventDao.insert(event)
    }

    companion object {
        @Volatile private var instance: EventRepository? = null

        fun getInstance(eventDao: EventDao)=
                instance ?: synchronized(this){
                    instance ?: EventRepository(eventDao).also { instance = it }
                }
    }

   /* @WorkerThread
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }*/

    /*fun fetchEvents() = launch{

        //allEvents = eventDao.getAllEvents()
        //some logic to see if its been fetched recently
        // Fetch from datasource
        *//*for (event in JuergappAPI.getInstance(context).getResource(Array<Event>::class.java)){
            eventDao.insert(event)
        }*//*
    }

    fun fetchEventById(id: Int) = launch {
        event.postValue(eventDao.getEvent(id.toString()).value)

        if (event.value == null){
            //event.value = JuergappAPI.getInstance(context).getResource(Event::class.java,id.toString())
        }
    }*/
}