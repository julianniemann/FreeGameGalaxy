package de.syntax.androidabschluss.ui

import HomeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.AuthenticationViewModel
import de.syntax.androidabschluss.GiveawayViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentHomeBinding

/**
 * Fragment for displaying home screen with giveaways.
 */
class HomeFragment : Fragment() {
    private val viewModel: GiveawayViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load giveaways based on selected platform and sort criteria
        if (viewModel.selectedPlatform != "1" && viewModel.selectedSortBy != "1") {
            viewModel.getGiveawaysSortedBy(viewModel.selectedPlatform, viewModel.selectedSortBy)
        } else {
            viewModel.loadGiveaways()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observe changes in the list of giveaways
        viewModel.giveaways.observe(viewLifecycleOwner) {
            // Set up the RecyclerView adapter
            binding.rvHome.adapter = HomeAdapter(it)
        }
        // Observe changes in the current user authentication status
        authenticationViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            // Navigate to login screen if user is not authenticated
            if (user == null) {
                findNavController().navigate(R.id.logInFragment)
            }
        }
    }
}


