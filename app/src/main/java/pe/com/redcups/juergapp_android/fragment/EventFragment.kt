package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_event.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.core.viewmodel.EventViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter

class EventFragment : Fragment() {

    private lateinit var adapter: EventAdapter
    private lateinit var viewModel:  EventViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add adapter and layout manager
        adapter = EventAdapter(view.context)
        recycler_view_event.adapter = adapter
        recycler_view_event.layoutManager= LinearLayoutManager(view.context)

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)

        viewModel.allEvents.observe(this, Observer { events ->
            adapter.setEvents(events)
            adapter.notifyDataSetChanged()
        })
        //viewModel.getEvents()

        //Animation option (change to slide from below)
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        // adds click listener
        event_add_button.setOnClickListener{
            // Este id dentro de Navigate es del que esta definido en navigation/nav_graph
            findNavController().navigate(R.id.event_add_dest, null, options)
        }


        // AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(view.context))

        //JuergappAPI.getResource(
        //    Array<Event>::class.java,
        //   {
        //        viewModel.setEvents(it)
        //    },
        //   {
        //      Log.d("error", it.toString())
        // }
        // )

    }

}
