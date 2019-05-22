package pe.com.redcups.core.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.repository.GameRepository

class GameViewModel internal constructor(gameRepository: GameRepository) : ViewModel() {

    val allGames: LiveData<List<Game>> = gameRepository.getAllGames()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /*init{
        val gameDao = JuergappDatabase.getDatabase(application,viewModelScope).gameDao()
        repository = GameRepository(gameDao, application.applicationContext)
        repository.fetchGames()
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

    fun getGames(){
        repository.fetchGames()
    }
    fun getGame(gameId: String): Game{
        return repository.getGame(gameId)
    }*/
}
