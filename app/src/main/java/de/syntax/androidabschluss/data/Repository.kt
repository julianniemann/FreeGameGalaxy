package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.data.local.FGGDatabase
import de.syntax.androidabschluss.data.models.Giveaway
import de.syntax.androidabschluss.data.remote.FreeGameGalaxyApi

/**
 * Repository class responsible for coordinating data operations between local and remote sources.
 * @param api The remote API service.
 * @param database The local database.
 */
class Repository(private val api: FreeGameGalaxyApi, private val database: FGGDatabase) {

    private val TAG = "Repository"

    // LiveData for fetching favorite giveaways from the local database
    val favorites: LiveData<List<Giveaway>> = database.fggDao.getAllFavorites()

    // MutableLiveData for storing giveaways fetched from the API
    private val _giveaways = MutableLiveData<List<Giveaway>>()
    val giveaways: LiveData<List<Giveaway>>
        get() = _giveaways

    /**
     * Fetch giveaways from the API.
     * @return List of fetched giveaways.
     */
    suspend fun getGiveaways(): List<Giveaway> {
        return try {
            val giveaways = api.retrofitService.getGiveaways()
            _giveaways.postValue(giveaways)
            giveaways
        } catch (e: Exception) {
            Log.e(TAG, "Error loading data from API", e)
            emptyList()
        }
    }

    /**
     * Fetch a giveaway by its ID from the API.
     * @param id The ID of the giveaway.
     * @return The fetched giveaway.
     */
    suspend fun getGiveawayById(id: Int): Giveaway {
        return try {
            api.retrofitService.getGiveawayById(id)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading data from API", e)
            throw e
        }
    }

    /**
     * Insert a giveaway into the local database.
     * @param giveaway The giveaway to insert.
     */
    suspend fun insertGiveaway(giveaway: Giveaway) {
        try {
            database.fggDao.insertGiveaway(giveaway)
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting giveaway into database", e)
        }
    }

    /**
     * Delete a giveaway from the local database.
     * @param giveaway The giveaway to delete.
     */
    suspend fun deleteGiveaway(giveaway: Giveaway) {
        try {
            database.fggDao.deleteGiveaway(giveaway)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting giveaway from database", e)
        }
    }

    /**
     * Fetch giveaways sorted by platform and sortedBy from the API.
     * @param platform The platform to filter the giveaways.
     * @param sortedBy The sorting criteria.
     */
    suspend fun getGiveawaysSortedBy(platform: String, sortedBy: String) {
        try {
            val giveawaysSortedBy = api.retrofitService.getGiveawaysSortedBy(platform, sortedBy)
            _giveaways.value = giveawaysSortedBy
        } catch (e: Exception) {
            Log.e(TAG, "Error loading sorted data", e)
        }
    }
}


