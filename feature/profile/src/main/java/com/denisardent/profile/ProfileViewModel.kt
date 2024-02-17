package com.denisardent.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisardent.common.AccountInfo
import com.denisardent.domain.GetAccountInfo
import com.denisardent.domain.GetProductsListUseCase
import com.denisardent.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAccountInfo: GetAccountInfo,
    private val getProductsListUseCase: GetProductsListUseCase
): ViewModel() {
    private val _profileState = MutableStateFlow<ProfileUiState>(
        ProfileUiState(
            AccountInfo("", "", ""),
            0
        )
    )
    val profileState = _profileState.asStateFlow()

    init{
        viewModelScope.launch {
            _profileState.emit(
                ProfileUiState(
                    getAccountInfo(),
                    getProductsListUseCase().filter { it.isLiked }.size
                )
            )
        }
    }

    fun getProductsCount(){
        viewModelScope.launch{
            _profileState.update {
                ProfileUiState(it.accountInfo,
                    getProductsListUseCase().filter { it.isLiked }.size)
            }
        }
    }

    fun logout(){
        logoutUseCase()
    }
}

data class ProfileUiState(
    val accountInfo: AccountInfo,
    val likedCounter: Int
)