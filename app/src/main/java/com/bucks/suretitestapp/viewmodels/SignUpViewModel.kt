package com.bucks.suretitestapp.viewmodels

import androidx.lifecycle.ViewModel
import com.bucks.suretitestapp.api.UserLoginResponse
import com.bucks.suretitestapp.data.SuretiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel  @Inject constructor(
    private val suretiRepository: SuretiRepository
) : ViewModel() {

    suspend fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userCellNo: String,
        mailingAddress: String
    ): UserLoginResponse {
        return suretiRepository.registerUser(email, password, firstName, lastName, userCellNo, mailingAddress)
    }
}