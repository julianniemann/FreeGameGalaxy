package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.GiveawayViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSettingsBinding

/**
 * Fragment for managing app settings.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: GiveawayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RadioGroup listeners for platform selection
        binding.radioGroupPlatform.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedPlatform = when (checkedId) {
                R.id.radioButtonPC -> "pc"
                R.id.radioButtonSwitch -> "switch"
                R.id.radioButtonPs5 -> "ps5"
                R.id.radioButtonXbox -> "xbox-one"
                R.id.radioButtonAndroid -> "android"
                R.id.radioButtoniOS -> "ios"
                else -> "1"
            }
        }

        // Check the appropriate radio button based on selected platform
        when (viewModel.selectedPlatform) {
            "pc" -> binding.radioGroupPlatform.check(R.id.radioButtonPC)
            "switch" -> binding.radioGroupPlatform.check(R.id.radioButtonSwitch)
            "ps5" -> binding.radioGroupPlatform.check(R.id.radioButtonPs5)
            "xbox-one" -> binding.radioGroupPlatform.check(R.id.radioButtonXbox)
            "android" -> binding.radioGroupPlatform.check(R.id.radioButtonAndroid)
            "ios" -> binding.radioGroupPlatform.check(R.id.radioButtoniOS)
            else -> binding.radioGroupPlatform.check(R.id.radioButtonPC) // Default selection
        }

        // Set up RadioGroup listeners for sorting options
        binding.radioGroupSortBy.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedSortBy = when (checkedId) {
                R.id.radioButtonDate -> "date"
                R.id.radioButtonValue -> "value"
                R.id.radioButtonPopularity -> "popularity"
                else -> "1"
            }
        }

        // Check the appropriate radio button based on selected sorting option
        when (viewModel.selectedSortBy) {
            "date" -> binding.radioGroupSortBy.check(R.id.radioButtonDate)
            "value" -> binding.radioGroupSortBy.check(R.id.radioButtonValue)
            "popularity" -> binding.radioGroupSortBy.check(R.id.radioButtonPopularity)
            else -> binding.radioGroupSortBy.check(R.id.radioButtonDate) // Default selection
        }
    }
}

