package pe.com.redcups.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.redcups.core.repository.EventRepository
import pe.com.redcups.core.repository.UserRepository
import pe.com.redcups.core.viewmodel.events.EventViewModel

class UserProfileViewModelFactory(
    private val repository: UserRepository,
    private val userId: String
): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>) = UserProfileViewModel(
        repository,
        userId
    ) as T
}