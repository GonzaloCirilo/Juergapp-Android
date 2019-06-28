package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_event.view.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.utilities.BitmapUtils
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.EventFragmentDirections
import pe.com.redcups.juergapp_android.options

class EventAdapter(context: Context): RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    private var events: List<Event> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with (holder){
            val event  = events[events.count()-1 - position] // reversing order
            eventTextView.text = event.name
            eventAddressTextView.text = event.address

            eventImageView.setImageResource(R.mipmap.event_image_placeholder)
            if (event.pictureData != null){
                eventImageView.setImageBitmap(BitmapUtils.stringToBitmap(event.pictureData!!))
            }
            eventImageView.contentDescription = event.name + " picture"
            itemView.setOnClickListener{
                // aca le pasas el argumento del evento por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = EventFragmentDirections.getDetailsAction(event.id.toString(), event.name)
                it.findNavController().navigate(action, options)
            }
        }
    }

    inner class ViewHolder(eventView: View): RecyclerView.ViewHolder(eventView) {
        val eventTextView: TextView = eventView.event_name
        val eventAddressTextView: TextView = eventView.event_address
        val eventImageView: ImageView = eventView.event_image
    }

    // updates Events Array

    fun setEvents(events: List<Event>){
        this.events = events
        this.notifyDataSetChanged()
    }
}