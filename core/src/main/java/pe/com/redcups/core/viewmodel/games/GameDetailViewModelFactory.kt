package pe.com.redcups.core.viewmodel.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.GameRepository

class GameDetailViewModelFactory (
    private val repository: GameRepository,
    private val gameId: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = GameDetailViewModel(
        repository,
        gameId
    ) as T
}