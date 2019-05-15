package pe.com.redcups.core.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI

class EventDataSource(context: Context): CoroutineScope by MainScope(){

    private val context = context;

    // get from API
    fun fetchEvents(): LiveData<List<Event>>{
        val data = MutableLiveData<List<Event>>()
         launch {
             data.value =  JuergappAPI.getInstance(context).getResource(Array<Event>::class.java).asList()
         }
        return data
    }
}