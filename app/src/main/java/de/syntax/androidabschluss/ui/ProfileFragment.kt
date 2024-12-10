package de.syntax.androidabschluss.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.AuthenticationViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentProfileBinding

/**
 * Fragment for displaying and editing user profile information.
 */
class ProfileFragment : Fragment() {

    private val viewModel: AuthenticationViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe current user's email address and update UI
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textViewEmail.text = getString(R.string.email, user.email)
                // Observe user's name and update EditText
                viewModel.name.observe(viewLifecycleOwner) { name ->
                    binding.editTextName.setText(name)
                }
                // Observe user's location and update EditText
                viewModel.location.observe(viewLifecycleOwner) { location ->
                    binding.editTextLocation.setText(location)
                }
                // Observe user's games and update EditText
                viewModel.games.observe(viewLifecycleOwner) { games ->
                    binding.editTextGames.setText(games)
                }
                // Observe user's about me and update EditText
                viewModel.aboutMe.observe(viewLifecycleOwner) { aboutMe ->
                    binding.editTextAboutMe.setText(aboutMe)
                }
            }
        }

        // Logout button click listener
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
        }

        // Set hints for EditText fields
        binding.editTextName.hint = getString(R.string.name)
        binding.editTextLocation.hint = getString(R.string.wohnort)
        binding.editTextGames.hint = getString(R.string.spiele)
        binding.editTextAboutMe.hint = getString(R.string.about_me_beschreibung)

        // Remove hint when EditText field is clicked
        binding.editTextName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateName(binding.editTextName.text.toString())
            }
        }
        binding.editTextLocation.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateLocation(binding.editTextLocation.text.toString())
            }
        }
        binding.editTextGames.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateGames(binding.editTextGames.text.toString())
            }
        }
        binding.editTextAboutMe.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateAboutMe(binding.editTextAboutMe.text.toString())
            }
        }
    }
}