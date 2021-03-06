package pe.com.redcups.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import pe.com.redcups.core.dao.*
import pe.com.redcups.core.helper.DateConverter
import pe.com.redcups.core.model.*
import pe.com.redcups.core.model.tx.OrderDetailTX
import pe.com.redcups.core.model.tx.OrderTX

@Database(entities = [
    User::class,
    Event::class,
    ProductCategory::class,
    Product::class,
    Game::class,
    OrderTX::class,
    OrderDetailTX::class,
    Order::class,
    OrderDetail::class,
    Supplier::class],
    version = 24)
@TypeConverters(DateConverter::class)
abstract class JuergappDatabase: RoomDatabase() {

    // Declare dao
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productDao(): ProductDao
    abstract fun gameDao(): GameDao
    abstract fun orderTXDao(): OrderTXDao
    abstract fun orderDetailTXDao(): OrderDetailTXDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao


    // singleton for database
    companion object {
        @Volatile
        private var INSTANCE: JuergappDatabase? = null

        fun getInstance(context: Context): JuergappDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): JuergappDatabase{
            return Room.databaseBuilder(context,JuergappDatabase::class.java,"Juergapp_database")
                .addCallback(object: RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // see android sunshine for more details
                        /*val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)*/
                    }
                })
                // this is so that database migrations can work without harm
                .fallbackToDestructiveMigration()
                // rebuild database
                .build()
        }
    }
}
