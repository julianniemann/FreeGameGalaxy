package de.syntax.androidabschluss.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import de.syntax.androidabschluss.GiveawayViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Giveaway
import de.syntax.androidabschluss.databinding.ItemFavoriteBinding
import de.syntax.androidabschluss.ui.FavoriteFragmentDirections

/**
 * Adapter class for handling favorite items in a RecyclerView.
 * @param dataset List of favorite items.
 * @param viewModel ViewModel for managing data.
 */
class FavoriteAdapter(
    private var dataset: List<Giveaway>,
    private val viewModel: GiveawayViewModel
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    /**
     * ViewHolder class for favorite items.
     * @param binding ViewBinding for the item layout.
     */
    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = dataset[position]
        // Set title
        holder.binding.textViewTitle.text = favorite.title
        // Load image with rounded corners
        holder.binding.imageViewThumbnail.load(favorite.thumbnail) {
            transformations(RoundedCornersTransformation(10f))
            placeholder(R.drawable.fgglogo)
        }
        // Handle click to navigate to detail fragment
        holder.binding.cvFavorites.setOnClickListener {
            val navController = holder.binding.cvFavorites.findNavController()
            navController.navigate(
                FavoriteFragmentDirections.actionFavoritenFragmentToDetailFragment(favorite.id ?: 0)
            )
        }
        // Handle click to delete favorite item
        holder.binding.deleteButton.setOnClickListener {
            viewModel.deleteGiveaway(favorite)
        }
    }
}