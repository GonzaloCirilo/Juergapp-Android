package pe.com.redcups.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.EventRepository

class EventViewModelFactory(
    private val repository: EventRepository
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = EventViewModel(repository) as T
}