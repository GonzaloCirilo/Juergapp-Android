package pe.com.redcups.core.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.repository.EventRepository
import pe.com.redcups.core.repository.GameRepository

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val allGames: LiveData<List<Game>>
    val repository: GameRepository

    init{
        val gameDao = JuergappDatabase.getDatabase(application, viewModelScope).gameDao()
        repository = GameRepository(gameDao)
        repository.fetchGames(application.applicationContext)
        allGames = repository.allGames
    }

    fun insert(game: Game) = viewModelScope.launch(Dispatchers.IO){
        Log.d("Inserting", game.id.toString())
        Log.d("Inserting", game.name)
        repository.insert(game)
    }
    fun setGames(games: Array<Game>){
        for (game in games){
            insert(game)
        }

    }
    fun getGames(context: Context) {

    }

    fun getGame(eventId: Int){

    }
}