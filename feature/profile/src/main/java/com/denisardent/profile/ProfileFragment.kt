package com.denisardent.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denisardent.presentation.viewBinding
import com.denisardent.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            likedCard.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_liked_nav_graph)
            }
            logout.setOnClickListener {
                viewModel.logout()
                startActivity(Intent(requireContext(), Class.forName("com.denisardent.authorization.LoginActivity")))
            }
        }
    }
}