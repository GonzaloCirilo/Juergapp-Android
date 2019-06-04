package pe.com.redcups.core.viewmodel.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.repository.EventRepository

class EventViewModel internal constructor(private val eventRepository: EventRepository) : ViewModel(){

    var events: LiveData<List<Event>> = eventRepository.getAllEvents().also { refreshAllEvents() }

    fun refreshAllEvents() = viewModelScope.launch (Dispatchers.IO) {
        runBlocking {
            eventRepository.refreshData()
        }
    }

    fun refresh(callback: ()->Unit) =
        viewModelScope.launch(Dispatchers.Main) { eventRepository.fetchEvents(callback) }


    fun insert(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        eventRepository.insertEvent(event)
    }
    fun setEvents(events: Array<Event>){
        for (event in events){
            insert(event)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}