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
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.juergapp_android.R
import java.util.concurrent.CountDownLatch

class GameDetailFragment : Fragment() {

    lateinit var game: Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: GameDetailFragmentArgs by navArgs()
        val gameId = safeArgs.gameId

        val signal = CountDownLatch(1)

        //crea el App Controller
        AppController.getInstance()
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(view.context))

        // Make request


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

        game_detail_description.text =  game.description
        game_detail_image.setImageResource(R.mipmap.event_image_placeholder)
    }
}