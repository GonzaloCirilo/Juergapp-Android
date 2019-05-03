package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_detail.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.JuergappAPI

import pe.com.redcups.juergapp_android.R
import java.util.concurrent.CountDownLatch

class EventDetailFragment : Fragment() {

    lateinit var event: Event

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signal = CountDownLatch(1)
        val safeArgs: EventDetailFragmentArgs by navArgs()
        val eventId = safeArgs.eventId

        event_name_label.text = "Evento #" + eventId;

        JuergappAPI.getResource(
            Event::class.java,
            {
                event = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            },
            "/$eventId"
        )

        signal.await()

        event_address.text = event.address

    }
}
