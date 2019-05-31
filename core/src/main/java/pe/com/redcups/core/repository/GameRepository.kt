package pe.com.redcups.core.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import pe.com.redcups.core.dao.GameDao
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.JuergappAPI


// TODO: Remove CoroutineScope
class GameRepository private constructor(private val gameDao: GameDao, private val context: Context): CoroutineScope by MainScope(){

    fun getAllGames(): LiveData<List<Game>> {
        fetchGames()
        return gameDao.getAllGames()
    }
    fun getGame(gameId: String) = gameDao.getGame(gameId)

    companion object {
        @Volatile private var instance: GameRepository? = null

        fun getInstance(gameDao: GameDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: GameRepository(gameDao, context).also { instance = it }
                }
    }

    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }
    fun fetchGames() = launch {
        //database

        //network
        //somelogic to validate last fetch
        for (game in  JuergappAPI.getInstance(context).getResource(Array<Game>::class.java)){
            gameDao.insert(game)
        }

    }
}
