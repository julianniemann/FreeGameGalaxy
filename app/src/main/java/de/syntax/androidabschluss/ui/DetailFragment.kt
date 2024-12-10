package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import de.syntax.androidabschluss.GiveawayViewModel
import de.syntax.androidabschluss.data.models.Giveaway
import de.syntax.androidabschluss.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

/**
 * Fragment for displaying details of a giveaway.
 */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: GiveawayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id") ?: 0

        var giveaway: Giveaway? = null

        if (id != 0) {
            lifecycleScope.launch {
                try {
                    // Fetch giveaway details by ID
                    giveaway = viewModel.getGiveawayById(id)
                    // Load giveaway details into UI elements
                    binding.imageViewThumbnail.load(giveaway?.thumbnail)
                    binding.textViewTitle.text = giveaway?.title
                    binding.textViewWorth.text = giveaway?.worth
                    binding.textViewDescription.text = giveaway?.description
                    binding.textViewInstructions.text = giveaway?.instructions
                    binding.textViewPlatforms.text = giveaway?.platforms
                } catch (e: Exception) {
                    Log.e("DetailFragment", "Error loading giveaway details: ${e.message}")
                }
            }
        } else {
            Log.e("DetailFragment", "No ID received")
        }

        // Handle click on favorite button
        binding.favoriteButton.setOnClickListener {
            giveaway?.let { nonNullableGiveaway ->
                // Insert giveaway into favorites
                viewModel.insertGiveaway(nonNullableGiveaway)
            }
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_LONG).show()
        }
    }
}

