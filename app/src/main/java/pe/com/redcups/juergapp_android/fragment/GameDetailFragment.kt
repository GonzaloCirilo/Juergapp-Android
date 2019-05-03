package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_game_detail.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.juergapp_android.R
import java.util.concurrent.CountDownLatch

class GameDetailFragment : Fragment() {

    lateinit var game: Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signal = CountDownLatch(1)
        val safeArgs = GameDetailFragment by navArgs()
        val gameId = safeArgs.gameId

        JuergappAPI.getResource(
            Game::class.java,
            {
                game = it
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            },
            "/$gameId"
        )

        signal.await()

        game_description_detail.text = game.description
    }*/
}