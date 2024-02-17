package com.denisardent.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denisardent.presentation.viewBinding
import com.denisardent.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getProductsCount()
    }

    private fun setUpUI(){
        with(binding){
            likedCard.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_liked_nav_graph)
            }
            logout.setOnClickListener {
                viewModel.logout()
                startActivity(Intent(requireContext(), Class.forName("com.denisardent.authorization.LoginActivity")))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collectLatest {state ->
                    with(binding){
                        val liked = state.likedCounter
                        nameSurname.text = "${state.accountInfo.name + " " + state.accountInfo.surname}"
                        phoneNumber.text = "+7${state.accountInfo.phone}"
                        likedCounter.text = if (liked == 0){
                            ""
                        } else if(liked%10 == 1){
                            "${liked} товар"
                        } else if (liked%10 == 2 || liked%10 == 3 || liked%10 == 4){
                            "${liked} товара"
                        } else{
                            "${liked} товаров"
                        }
                    }
                }
            }
        }
    }
}