package pe.wemake.watch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_recycler_view.view.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.core.utilities.BitmapUtils

class EventAdapter(context: Context): RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    private var events: List<Event> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.event_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with (holder){
            var event  = events[position]
            eventTextView.text = event.name
            eventAddressTextView.text = event.address

            if (event.picture_data != null){
                eventImageView.setImageBitmap(BitmapUtils.stringToBitmap(event.picture_data!!))
            }

        }
    }

    inner class ViewHolder(eventView: View): RecyclerView.ViewHolder(eventView) {
        val eventTextView: TextView = eventView.event_name
        val eventAddressTextView: TextView = eventView.event_address
        val eventImageView: ImageView = eventView.image
    }

    // updates Events Array

    fun setEvents(events: List<Event>){
        this.events = events
        this.notifyDataSetChanged()
    }
}