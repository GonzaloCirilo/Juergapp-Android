package pe.com.redcups.core.repository

import android.content.Context
import pe.com.redcups.core.dao.GameDao
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.network.JuergappAPI

class GameRepository private constructor(private val gameDao: GameDao){

    fun getAllGames() = gameDao.getAllGames()

    fun getGame(gameId: String) = gameDao.getGame(gameId)

    companion object {
        @Volatile private var instance: GameRepository? = null

        fun getInstance(gameDao: GameDao, context: Context) =
                instance ?: synchronized(this){
                    instance ?: GameRepository(gameDao).also {
                        instance = it
                        JuergappAPI.getInstance(context)
                    }
                }
    }

    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }

    suspend fun fetchGames() {
        for (game in  JuergappAPI.getInstance().getResource(Array<Game>::class.java)){
            gameDao.insert(game)
        }
    }
}
