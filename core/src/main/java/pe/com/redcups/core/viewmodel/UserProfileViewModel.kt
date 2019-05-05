package pe.com.redcups.core.viewmodel

import androidx.lifecycle.ViewModel
import pe.com.redcups.core.model.User

class UserProfileViewModel : ViewModel() {
    private var userId: String? = null
    val user: User? = null

    fun init(userId: String) {
        this.userId = userId
    }
    // init {
        // val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        // repository = WordRepository(wordsDao)
        // allWords = repository.allWords
    // }

    // private val repository: WordRepository
    // val allWords: LiveData<List<Word>>


    // fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        // repository.insert(word)
    // }
}



