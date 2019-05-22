package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_game.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.GameViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.GameAdapter
import java.util.concurrent.CountDownLatch

class GameFragment : Fragment() {

    private lateinit var adapter: GameAdapter
    private val viewModel:  GameViewModel by viewModels {
        InjectorUtils.provideGameViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameAdapter(view.context)

        recycler_view_game.adapter = adapter
        recycler_view_game.layoutManager = LinearLayoutManager(view.context)

        viewModel.allGames.observe(this, Observer { games ->
            adapter.setGames(games)
        })
    }
}
