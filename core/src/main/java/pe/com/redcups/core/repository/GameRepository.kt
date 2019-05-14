package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import pe.com.redcups.core.dao.GameDao
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.JuergappAPI

class GameRepository(private val gameDao: GameDao){

    val allGames: MutableLiveData<List<Game>> = MutableLiveData<List<Game>>()

    init{
         allGames.value = gameDao.getAllGames().value
    }

    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }

    fun fetchGames(context: Context){
        //database
        allGames.value = gameDao.getAllGames().value

        //network
        //somelogic to validate last fetch
        //runBlocking{
         //   allGames.value = JuergappAPI.getInstance(context).getResource(Array<Game>::class.java)
        // }

    }
}
