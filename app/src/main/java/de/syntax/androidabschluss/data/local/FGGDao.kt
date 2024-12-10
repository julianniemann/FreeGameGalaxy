package de.syntax.androidabschluss.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax.androidabschluss.data.models.Giveaway

/**
 * Data Access Object (DAO) interface for accessing giveaway data in the local database.
 */
@Dao
interface FGGDao {

    /**
     * Insert a giveaway into the database.
     * @param giveaway The giveaway to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiveaway(giveaway: Giveaway)

    /**
     * Delete a giveaway from the database.
     * @param giveaway The giveaway to delete.
     */
    @Delete
    suspend fun deleteGiveaway(giveaway: Giveaway)

    /**
     * Get all favorite giveaways from the database.
     * @return LiveData containing a list of favorite giveaways.
     */
    @Query("SELECT * FROM giveaway")
    fun getAllFavorites(): LiveData<List<Giveaway>>
}