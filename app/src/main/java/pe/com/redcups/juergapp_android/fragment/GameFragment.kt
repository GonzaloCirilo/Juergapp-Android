package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_game.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.GameAdapter
import java.util.concurrent.CountDownLatch

class GameFragment : Fragment() {

    private lateinit var games: Array<Game>
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: get viewmodel
        gameAdapter = GameAdapter(games, view.context)
        recycler_view_game.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }
}
