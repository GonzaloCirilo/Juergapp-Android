package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_game.view.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.utilities.BitmapUtils
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.GameFragmentDirections
import pe.com.redcups.juergapp_android.options

class GameAdapter(context: Context): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private var games: List<Game> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        val view: View =  inflater.inflate(R.layout.recycler_view_game, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {

        var game = games[position]

        with(holder){

            gameImageView.setImageResource(R.mipmap.event_image_placeholder)
            if (game.pictureData != null){
                gameImageView.setImageBitmap(BitmapUtils.stringToBitmap(game.pictureData!!))
            }
            gameTextView.text = game.name
            itemView.setOnClickListener{
                // Le pasas el argumento del producto por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = GameFragmentDirections.getGameDetailAction(game.id.toString(), game.name )
                itemView.findNavController().navigate(action, options)
            }
            //}
        }
    }

    inner class ViewHolder(gameView: View): RecyclerView.ViewHolder(gameView){
        val gameImageView: ImageView = gameView.game_image
        val gameTextView: TextView = gameView.game_name


    }
    // updates Games Array
    fun setGames(games: List<Game>){
        this.games = games
        this.notifyDataSetChanged()
    }

}