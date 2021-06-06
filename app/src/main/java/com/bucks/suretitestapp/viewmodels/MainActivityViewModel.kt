package com.bucks.suretitestapp.viewmodels

import androidx.lifecycle.ViewModel
import com.bucks.suretitestapp.api.ActiveChecksResponse
import com.bucks.suretitestapp.api.UserLoginResponse
import com.bucks.suretitestapp.data.SuretiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val suretiRepository: SuretiRepository
) : ViewModel() {

    suspend fun getActiveChecks(token: String): ActiveChecksResponse {
        return suretiRepository.getActiveChecks(token)
    }
}