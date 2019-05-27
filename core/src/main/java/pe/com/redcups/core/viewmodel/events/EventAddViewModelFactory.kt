package pe.com.redcups.core.viewmodel.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.EventRepository

class EventAddViewModelFactory(
    private val repository: EventRepository
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = EventAddViewModel(repository) as T
}