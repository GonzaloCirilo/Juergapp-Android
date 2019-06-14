package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_event.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter
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
        viewModel.events.observe(this, Observer{
            adapter.setEvents(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventAdapter(view.context)

        recycler_view_event.adapter = adapter
        recycler_view_event.layoutManager= LinearLayoutManager(view.context)

        viewModel.events.observe(this, Observer {
            adapter.setEvents(it)
        })

        swipe.setColorSchemeColors(ContextCompat.getColor(context!!,R.color.colorPrimary))
        swipe.setOnRefreshListener {
            viewModel.refresh {
                swipe?.let {
                    it.isRefreshing = false
                }
            }
        }

        // adds click listener
        event_add_button.setOnClickListener{
            // Este id dentro de Navigate es del que esta definido en navigation/nav_graph
            findNavController().navigate(R.id.event_add_dest, null, options)
        }
    }
}

