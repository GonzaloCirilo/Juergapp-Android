package pe.com.redcups.core.viewmodel.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.repository.GameRepository

class GameViewModel internal constructor(private val gameRepository: GameRepository) : ViewModel() {

    val allGames: LiveData<List<Game>> = gameRepository.getAllGames().also { refreshAllGames() }

    fun refreshAllGames() = viewModelScope.launch(Dispatchers.IO) {
        runBlocking {
            gameRepository.fetchGames()
        }
    }

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
