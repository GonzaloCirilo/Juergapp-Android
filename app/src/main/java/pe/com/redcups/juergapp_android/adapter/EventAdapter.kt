package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_event.view.*
import pe.com.redcups.core.model.Event
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.EventDetailFragment

class EventAdapter(private val events: ArrayList<Event>, context: Context): RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventTextView.text = events[position].name
        holder.eventImageView.setImageResource(R.mipmap.event_image_placeholder)

    }

    inner class ViewHolder(eventView: View): RecyclerView.ViewHolder(eventView) {
        val eventTextView: TextView = eventView.event_name
        val eventImageView: ImageView = eventView.event_image

        init {
            with(itemView){
                eventView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.event_detail_dest))
            }
        }

    }
}