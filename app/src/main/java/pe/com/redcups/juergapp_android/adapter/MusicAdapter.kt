package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_music.view.*
import pe.com.redcups.core.model.Track
import pe.com.redcups.juergapp_android.R

class MusicAdapter(private val tracks: List<Track>, context: Context): RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    private var inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recycler_view_music, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            name.text = tracks[position].trackName
            artist.text = tracks[position].artist
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.track_name
        val artist: TextView = itemView.track_artist
    }
}