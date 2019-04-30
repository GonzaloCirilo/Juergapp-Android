package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
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

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.EventAdapter

class EventFragment : Fragment() {

    private var events = ArrayList<Event>()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // reseteo la lista porque sino Cuando regresas se
        // vuelven a agregar 20 eventos
        // y en total hay 40
        events =  ArrayList<Event>();
        for (i in 1..20){
            events.add(Event(i,"Event $i", i))
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
        view?.findViewById<FloatingActionButton>(R.id.event_add_button)?.setOnClickListener{
            // Este id dentro de Navigate es del que esta definido en navigation/nav_graph
            findNavController().navigate(R.id.event_add_dest, null, options)
        }
    }


}
