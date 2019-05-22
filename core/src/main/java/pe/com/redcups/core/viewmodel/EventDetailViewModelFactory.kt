package pe.com.redcups.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.EventRepository

class EventDetailViewModelFactory(
    private val repository: EventRepository,
    private val eventId: String
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = EventDetailViewModel(repository, eventId) as T
}