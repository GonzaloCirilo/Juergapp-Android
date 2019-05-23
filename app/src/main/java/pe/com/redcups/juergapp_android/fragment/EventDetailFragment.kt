package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventDetailViewModel

import pe.com.redcups.juergapp_android.R

class EventDetailFragment : Fragment() {

    private val safeArgs: EventDetailFragmentArgs by navArgs()

    private val viewModel: EventDetailViewModel by viewModels{
        InjectorUtils.provideEventDetailViewModelFactory(requireActivity(), safeArgs.eventId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(this, Observer {
            it?.also { e ->
                event_name_label.text = e.name
                event_address.text = e.address
            }
        })


    }
}
