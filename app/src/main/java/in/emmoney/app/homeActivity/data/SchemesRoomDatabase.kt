package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.AllSchemesEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AllSchemesEntity::class], version = 1, exportSchema = false)
abstract class SchemesRoomDatabase : RoomDatabase(){

    abstract fun getAllSchemesDao(): AllSchemesDao

    // Companion: members  of this class can be called without instantiating the class
    companion object {

        // Creating nullable variable INSTANCE
        @Volatile
        var INSTANCE: SchemesRoomDatabase? = null

        fun getDatabase(context: Context): SchemesRoomDatabase {

            // Using the function "synchronized" so that only one thread can access this code at a time;
            // to avoid multiple reference of DB at the same time
            synchronized(this) {
                var instance = INSTANCE
                // Creating the instance of the Database if not present already.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SchemesRoomDatabase::class.java,
                        "schemes_database"
//                    ).build()
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

            // Another way of doing the above
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    SchemesRoomDatabase::class.java,
//                    "schemes_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
        }
    }
}