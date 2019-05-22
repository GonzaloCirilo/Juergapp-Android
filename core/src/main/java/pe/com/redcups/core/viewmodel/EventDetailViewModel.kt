package pe.com.redcups.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.repository.EventRepository

class EventDetailViewModel internal constructor(eventRepository: EventRepository, eventId: String): ViewModel(){

    val event: LiveData<Event> = eventRepository.getEvent(eventId)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}