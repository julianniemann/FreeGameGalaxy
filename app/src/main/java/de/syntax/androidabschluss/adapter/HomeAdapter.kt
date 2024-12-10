import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.models.Giveaway
import de.syntax.androidabschluss.databinding.ItemGiveawayBinding
import de.syntax.androidabschluss.ui.HomeFragmentDirections

/**
 * Adapter class for handling giveaway items in the home screen RecyclerView.
 * @param dataset List of giveaway items.
 */
class HomeAdapter(val dataset: List<Giveaway>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    /**
     * ViewHolder class for giveaway items.
     * @param binding ViewBinding for the item layout.
     */
    inner class HomeViewHolder(val binding: ItemGiveawayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemGiveawayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentGiveaway = dataset[position]

        // Set the data of the current giveaway into the view elements
        holder.binding.apply {
            textViewTitle.text = currentGiveaway.title
            imageViewThumbnail.load(currentGiveaway.thumbnail) {
                transformations(RoundedCornersTransformation(10f))
                placeholder(R.drawable.fgglogo)
            }
        }

        // Handle click to navigate to detail fragment
        holder.binding.cvGiveaway.setOnClickListener {
            val navController = holder.binding.cvGiveaway.findNavController()
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentGiveaway.id ?: 0)
            )
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
