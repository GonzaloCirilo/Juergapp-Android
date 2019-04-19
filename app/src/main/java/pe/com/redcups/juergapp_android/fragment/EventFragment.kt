package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_event.*
import pe.com.redcups.core.model.Event

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter

class EventFragment : Fragment() {

    private var events = ArrayList<Event>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        for (i in 1..10){
            events.add(Event("Event $i", i))
        }
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventAdapter = EventAdapter(events,view.context)
        recycler_view_event.apply{
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }


}
