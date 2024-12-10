package de.syntax.androidabschluss.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax.androidabschluss.data.models.Giveaway

/**
 * Room database class for the FGG app.
 */
@Database(entities = [Giveaway::class], version = 1)
abstract class FGGDatabase : RoomDatabase() {
    abstract val fggDao: FGGDao
}

private lateinit var INSTANCE: FGGDatabase

/**
 * Function to get an instance of the FGGDatabase.
 * @param context The application context.
 * @return Instance of the FGGDatabase.
 */

fun getDatabase(context: Context): FGGDatabase {
    synchronized(FGGDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FGGDatabase::class.java,
                "FGG_Database"
            ).build()
        }
    }
    return INSTANCE
}