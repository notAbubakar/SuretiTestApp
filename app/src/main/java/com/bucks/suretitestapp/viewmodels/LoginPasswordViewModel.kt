package com.bucks.suretitestapp.viewmodels

import androidx.lifecycle.ViewModel
import com.bucks.suretitestapp.api.UserLoginResponse
import com.bucks.suretitestapp.data.SuretiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginPasswordViewModel @Inject constructor(
    private val suretiRepository: SuretiRepository
) : ViewModel()  {

    suspend fun getUserLogin(email: String, password: String): UserLoginResponse {
        return suretiRepository.getUserLogin(email, password)
    }
}