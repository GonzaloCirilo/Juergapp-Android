package pe.com.redcups.core.viewmodel.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.GameRepository

class GameViewModelFactory (
    private val repository: GameRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = GameViewModel(
        repository
    ) as T
}