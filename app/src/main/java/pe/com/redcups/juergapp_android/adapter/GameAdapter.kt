package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_game.view.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.juergapp_android.R

class GameAdapter(private val games: ArrayList<Game>, context: Context): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        val view: View =  inflater.inflate(R.layout.recycler_view_game, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        holder.gameImageView.setImageResource(R.mipmap.event_image_placeholder)
        holder.gameTextView.text = games[position].name
    }

    inner class ViewHolder(gameView: View): RecyclerView.ViewHolder(gameView){

        val gameImageView: ImageView = gameView.game_image
        val gameTextView: TextView = gameView.game_name
    }

}