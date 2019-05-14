package pe.com.redcups.core.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.repository.EventRepository

class EventViewModel(application: Application) : AndroidViewModel(application){

    private val repository: EventRepository
    val allEvents: LiveData<List<Event>>

    init {
        val eventsDao = JuergappDatabase.getDatabase(application, viewModelScope).eventDao()
        repository = EventRepository(eventsDao)
        repository.fetchEvents()
        allEvents = repository.allEvents
        Log.d("size of repository all events", allEvents.value?.size.toString())
    }

    fun getEvent(eventId: Int):Event {
        //get from resource
        return allEvents.value!![eventId]
    }

    fun insert(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        //for debugging
        Log.d("Inserting", event.id.toString())
        Log.d("Inserting", event.name)
        repository.insert(event)
    }
    fun setEvents(events: Array<Event>){
        for (event in events){
            insert(event)
        }

    }
     fun  getEvents(context: Context): LiveData<List<Event>> {
         // Get events from repository
         repository.fetchEvents()
        return allEvents
    }

}