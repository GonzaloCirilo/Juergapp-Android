package pe.com.redcups.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import pe.com.redcups.core.model.Event

@Dao
interface EventDao{

    @Query("SELECT * from event_table ORDER BY id ASC")
    fun getAllEvents(): LiveData<List<Event>>


    @Query("SELECT * FROM event_table WHERE id = :id")
    fun getEvent(id: String): Event

    @Insert(onConflict = REPLACE)
    suspend fun insert(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("DELETE FROM event_table")
    fun deleteAll()

}
