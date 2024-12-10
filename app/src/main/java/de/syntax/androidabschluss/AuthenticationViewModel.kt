package de.syntax.androidabschluss

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * ViewModel for managing user authentication and profile information.
 */
class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "AuthenticationViewModel"

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    private val _games = MutableLiveData<String>()
    val games: LiveData<String> = _games

    private val _aboutMe = MutableLiveData<String>()
    val aboutMe: LiveData<String> = _aboutMe

    init {
        // Update the current user object during initialization
        updateCurrentUser()
    }

    private fun updateCurrentUser() {
        _currentUser.value = firebaseAuth.currentUser
    }

    /**
     * Signs up the user with the provided email and password.
     * After successful signup, automatically logs in the user.
     */
    fun signup(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                login(email, password)
            } else {
                Log.e(TAG, "Signup failed: ${it.exception}")
            }
        }
    }

    /**
     * Logs in the user with the provided email and password.
     */
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                updateCurrentUser()
            } else {
                Log.e(TAG, "Login failed: ${it.exception}")
            }
        }
    }

    /**
     * Logs out the current user.
     */
    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }

    /**
     * Updates the user's name.
     */
    fun updateName(name: String) {
        _name.value = name
    }

    /**
     * Updates the user's location.
     */
    fun updateLocation(location: String) {
        _location.value = location
    }

    /**
     * Updates the user's favorite games.
     */
    fun updateGames(games: String) {
        _games.value = games
    }

    /**
     * Updates the user's about me information.
     */
    fun updateAboutMe(aboutMe: String) {
        _aboutMe.value = aboutMe
    }
}