package pe.com.redcups.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import pe.com.redcups.core.model.User
import pe.com.redcups.core.repository.UserRepository

class UserProfileViewModel internal constructor(userRepository: UserRepository, userId: String) : ViewModel() {
    val user: LiveData<User> = userRepository.getUser(userId)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}



