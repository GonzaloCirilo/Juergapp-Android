package pe.com.redcups.core.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.repository.EventRepository

class EventViewModel internal constructor(eventRepository: EventRepository) : ViewModel(){

    val allEvents: LiveData<List<Event>> = eventRepository.getAllEvents()


    fun insert(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        //for debugging
        Log.d("Inserting", event.id.toString())
        Log.d("Inserting", event.name)
        //eventRepository.insert(event)
    }
    fun setEvents(events: Array<Event>){
        for (event in events){
            insert(event)
        }
    }
     /*fun  getEvents() {
         // Get events from repository
         repository.fetchEvents()
    }*/

}