package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_game_detail.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.GameDetailViewModel
import pe.com.redcups.core.viewmodel.GameViewModel
import pe.com.redcups.juergapp_android.R
import java.util.concurrent.CountDownLatch

class GameDetailFragment : Fragment() {
    private val safeArgs: GameDetailFragmentArgs by navArgs()
    private lateinit var game: Game
    private val viewModel: GameDetailViewModel by viewModels {
        InjectorUtils.provideGameDetailViewModelFactory(requireContext(), safeArgs.gameId.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        game_detail_image.setImageResource(R.mipmap.event_image_placeholder)

        viewModel.game.observe(this, Observer {
            game_detail_description.text = it.description
        })
    }
}