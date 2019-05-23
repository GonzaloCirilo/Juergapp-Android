package pe.com.redcups.core.viewmodel.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.repository.GameRepository

class GameDetailViewModel internal constructor(gameRepository: GameRepository, gameId: String): ViewModel(){
    val game: LiveData<Game> = gameRepository.getGame(gameId)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}