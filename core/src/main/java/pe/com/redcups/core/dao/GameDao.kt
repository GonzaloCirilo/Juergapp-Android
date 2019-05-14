package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pe.com.redcups.core.model.Game

@Dao
interface GameDao{

    @Query("SELECT * from game_table ORDER BY id ASC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM game_table WHERE id = :id")
    fun getGame(id: String): Game

    @Insert(onConflict = REPLACE)
    suspend fun insert(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM game_table")
    fun deleteAll()

}