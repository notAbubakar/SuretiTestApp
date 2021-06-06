package com.bucks.suretitestapp.viewmodels

import androidx.lifecycle.ViewModel
import com.bucks.suretitestapp.api.UserLoginResponse
import com.bucks.suretitestapp.data.SuretiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginEmailViewModel @Inject constructor(
    private val suretiRepository: SuretiRepository
) : ViewModel() {

    suspend fun isValidToken(token: String): UserLoginResponse {
        return suretiRepository.isValidToken(token)
    }

    suspend fun getDoesUserExist(email: String): String {
        return suretiRepository.getDoesUserExist(email)
    }
}