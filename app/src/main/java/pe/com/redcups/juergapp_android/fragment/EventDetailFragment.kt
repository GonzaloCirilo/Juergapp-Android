package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.viewmodel.EventViewModel

import pe.com.redcups.juergapp_android.R

class EventDetailFragment : Fragment() {

    lateinit var event: Event
    private lateinit var viewModel:  EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: EventDetailFragmentArgs by navArgs()
        val eventId = safeArgs.eventId

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)

        event = viewModel.getEvent(eventId.toInt())

    }
}
