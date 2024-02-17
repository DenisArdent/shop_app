package com.denisardent.authorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.denisardent.authorization.databinding.FragmentAuthorizationBinding
import com.denisardent.common.validateField
import com.denisardent.presentation.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    private val binding by viewBinding<FragmentAuthorizationBinding>()
    private val viewModel by viewModels<AuthorizationViewModel>()
    private val _isButtonShouldActive = MutableStateFlow(AuthorizationValid(false, false, false))



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.checkLogged()){
            startMain()
        }
        binding.nameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.validateField() ?: false){
                binding.nameTextInput.error = null
                _isButtonShouldActive.update {
                    AuthorizationValid(true, it.surnameValid, it.phoneValid)
                }
            } else {
                binding.nameTextInput.error = "Невалидный ввод"
                _isButtonShouldActive.update {
                    AuthorizationValid(false, it.surnameValid, it.phoneValid)
                }
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(binding.nameEditText.text.toString(), binding.surnameEditText.text.toString(), binding.phoneEditText.text.toString())
            startMain()
        }

        binding.surnameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.validateField() ?: false){
                binding.surnameTextInput.error = null
                _isButtonShouldActive.update {
                    AuthorizationValid(it.nameValid, true, it.phoneValid)
                }
            } else {
                binding.surnameTextInput.error = "Невалидный ввод"
                _isButtonShouldActive.update {
                    AuthorizationValid(it.nameValid, false, it.phoneValid)
                }
            }
        }

        binding.phoneEditText.doOnTextChanged { text, start, before, count ->
            if (text?.length == 10){
                binding.phoneTextInput.error = null
                _isButtonShouldActive.update {
                    AuthorizationValid(it.nameValid, it.surnameValid, true)
                }
            } else {
                binding.phoneTextInput.error = "Невалидный ввод"
                _isButtonShouldActive.update {
                    AuthorizationValid(it.nameValid, it.surnameValid, false)
                }
            }
        }


        lifecycleScope.launch {
            _isButtonShouldActive.collect{ state ->
                binding.loginButton.isEnabled = (state.nameValid) and (state.surnameValid) and (state.phoneValid)
            }
        }
    }



    fun startMain(){
        val intent = Intent(requireContext(), Class.forName("com.denisardent.testcase.MainActivity"))
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

data class AuthorizationValid(
    val nameValid: Boolean,
    val surnameValid: Boolean,
    val phoneValid: Boolean
)