package com.denisardent.profile

import androidx.lifecycle.ViewModel
import com.denisardent.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase): ViewModel() {


    fun logout(){
        logoutUseCase()
    }
}