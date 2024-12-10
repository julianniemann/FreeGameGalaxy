package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.GiveawayViewModel
import de.syntax.androidabschluss.adapter.FavoriteAdapter
import de.syntax.androidabschluss.databinding.FragmentFavoritenBinding

/**
 * Fragment for displaying favorite giveaways.
 */
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoritenBinding
    private val viewModel: GiveawayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observe changes in the list of favorite giveaways
        viewModel.favorite.observe(viewLifecycleOwner) { favorites ->
            // Set up the RecyclerView adapter
            val adapter = FavoriteAdapter(favorites, viewModel)
            binding.rvFavorites.adapter = adapter
        }
    }
}