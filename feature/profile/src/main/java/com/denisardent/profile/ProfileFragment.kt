package com.denisardent.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denisardent.presentation.viewBinding
import com.denisardent.profile.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding<FragmentProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.likedCard.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_liked_nav_graph)
        }
    }
}