package de.syntax.androidabschluss

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import de.syntax.androidabschluss.databinding.ActivityMainBinding

/**
 * Main activity hosting the navigation graph.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Navigation Controller
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.navController

        // BottomNavigationView setup
        binding.bottomNavigationView.setupWithNavController(navController)

        // Hide bottom navigation view on specific destinations
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.logInFragment, R.id.signUpFragment -> binding.bottomNavigationView.visibility =
                    View.GONE

                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView, navHost.navController, false
        )
    }
}
