package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter
import java.util.concurrent.CountDownLatch

class EventFragment : Fragment() {

    private lateinit var events: Array<Event>
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_event, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepara el latch
        val signal =  CountDownLatch(1)

        AppController.getInstance()
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(view.context))

        JuergappAPI.getResource(
            Array<Event>::class.java,
            {
                events = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            }
        )

        signal.await()

        eventAdapter = EventAdapter(events,view.context)
        recycler_view_event.apply{
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
        //Navigation for floating Add button

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
    }


}
