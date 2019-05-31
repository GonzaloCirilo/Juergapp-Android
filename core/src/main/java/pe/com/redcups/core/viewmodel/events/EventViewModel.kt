package pe.com.redcups.core.viewmodel.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.repository.EventRepository

class EventViewModel internal constructor(private val eventRepository: EventRepository) : ViewModel(){

    val allEvents: LiveData<List<Event>> = eventRepository.getAllEvents()

    fun refresh(callback: ()->Unit){
        eventRepository.refreshData(callback)
    }


    fun insert(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        //eventRepository.insert(event)
    }
    fun setEvents(events: Array<Event>){
        for (event in events){
            insert(event)
        }
    }

}