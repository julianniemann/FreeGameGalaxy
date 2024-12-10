package de.syntax.androidabschluss

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.Repository
import de.syntax.androidabschluss.data.local.getDatabase
import de.syntax.androidabschluss.data.models.Giveaway
import de.syntax.androidabschluss.data.remote.FreeGameGalaxyApi
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing giveaway data and interactions.
 */
class GiveawayViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "GiveawayViewModel"

    // Selected platform and sort by criteria
    var selectedPlatform: String = "1"
    var selectedSortBy: String = "1"

    // Database and repository instances
    private val database = getDatabase(application)
    private val repository = Repository(FreeGameGalaxyApi, database)

    // LiveData for list of giveaways and loading state
    val giveaways: LiveData<List<Giveaway>> = repository.giveaways
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // LiveData for error messages
    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> get() = _error

    // LiveData for favorite giveaways
    val favorite: LiveData<List<Giveaway>> = repository.favorites

    /**
     * Loads giveaways from the API.
     */
    fun loadGiveaways() {
        viewModelScope.launch {
            try {
                _loading.value = true
                repository.getGiveaways()
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                _error.value = "Error loading giveaways: ${e.message}"
                Log.e(TAG, "Error loading giveaways: ${e.message}")
            }
        }
    }

    /**
     * Retrieves a giveaway by its ID.
     */
    suspend fun getGiveawayById(id: Int): Giveaway {
        return repository.getGiveawayById(id)
    }

    /**
     * Inserts a giveaway into the database.
     */
    fun insertGiveaway(giveaway: Giveaway) {
        viewModelScope.launch {
            repository.insertGiveaway(giveaway)
        }
    }

    /**
     * Deletes a giveaway from the database.
     */
    fun deleteGiveaway(giveaway: Giveaway) {
        viewModelScope.launch {
            repository.deleteGiveaway(giveaway)
        }
    }

    /**
     * Retrieves giveaways sorted by platform and criteria.
     */
    fun getGiveawaysSortedBy(platform: String, sortedBy: String) {
        viewModelScope.launch {
            repository.getGiveawaysSortedBy(platform, sortedBy)
        }
    }
}