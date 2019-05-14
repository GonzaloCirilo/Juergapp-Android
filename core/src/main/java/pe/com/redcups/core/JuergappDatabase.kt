package pe.com.redcups.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.dao.*
import pe.com.redcups.core.model.*

@Database(entities = [User::class, Event::class, ProductCategory::class, Product::class, Game::class], version = 1)
abstract class JuergappDatabase: RoomDatabase() {

    // Declare dao
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productDao(): ProductDao
    abstract fun gameDao(): GameDao

    // singleton for database
    companion object {
        @Volatile
        private var INSTANCE: JuergappDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): JuergappDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JuergappDatabase::class.java,
                    "Juergapp_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                 .build()
                 INSTANCE = instance
                instance
            }
        }
        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.eventDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(eventDao: EventDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //eventDao.deleteAll()

            //var word = Event("Hello")
            //wordDao.insert(word)
            //word = Word("World!")
            //wordDao.insert(word)
        }
    }
}