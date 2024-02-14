package com.denisardent.authorization

import androidx.lifecycle.ViewModel
import com.denisardent.common.isCyrillic
import com.denisardent.domain.CheckLoggedUseCase
import com.denisardent.domain.LoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val getLoggedUseCase: CheckLoggedUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    fun checkLogged(): Boolean{
        return getLoggedUseCase()
    }
    fun login(name: String, surName: String, phone: String){
        loginUseCase(name, surName, phone)
    }
}