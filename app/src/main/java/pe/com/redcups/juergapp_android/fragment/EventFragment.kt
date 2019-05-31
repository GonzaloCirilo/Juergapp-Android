package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter
import pe.com.redcups.juergapp_android.adapter.ParticipantAdapter
import pe.com.redcups.juergapp_android.options

class EventFragment : Fragment() {

    private lateinit var adapter: EventAdapter
    private val viewModel: EventViewModel by viewModels{
        InjectorUtils.provideEventViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.allEvents.observeForever{
            adapter.setEvents(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add adapter and layout manager
        adapter = EventAdapter(view.context)

        recycler_view_event.adapter = adapter
        recycler_view_event.layoutManager= LinearLayoutManager(view.context)


        //AppController.getInstance(view.context)
        viewModel.allEvents.observeForever{
            adapter.setEvents(it)
        }

        swipe.setOnRefreshListener {
           swipe.isRefreshing = false
        }

        // adds click listener
        event_add_button.setOnClickListener{
            // Este id dentro de Navigate es del que esta definido en navigation/nav_graph
            findNavController().navigate(R.id.event_add_dest, null, options)
        }
    }
}

