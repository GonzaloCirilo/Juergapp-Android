package pe.com.redcups.core.viewmodel.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.repository.EventRepository

class EventAddViewModel(private val repository: EventRepository): ViewModel() {

    private val _event = MutableLiveData(
        Event(name = "Nueva Juerga")
    )

    var event: LiveData<Event> = _event

    fun persistEvent() = viewModelScope.launch(Dispatchers.IO){
        repository.insertEvent(event.value!!)
    }
}