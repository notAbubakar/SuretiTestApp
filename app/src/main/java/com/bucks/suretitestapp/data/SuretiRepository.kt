package com.bucks.suretitestapp.data

import com.bucks.suretitestapp.api.ActiveChecksResponse
import com.bucks.suretitestapp.api.SuretiApi
import com.bucks.suretitestapp.api.UserLoginResponse
import javax.inject.Inject

class SuretiRepository @Inject constructor(
    private val suretiApi: SuretiApi
) {
    suspend fun getDoesUserExist(email: String): String {
        val response = suretiApi.getDoesUserExist(email)
        return response.message
    }

    suspend fun getUserLogin(email: String, password: String): UserLoginResponse {
        return suretiApi.getUserLogin(email, password)
    }

    suspend fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userCellNo: String,
        mailingAddress: String
    ): UserLoginResponse {
        return suretiApi.registerUser(email, password, firstName, lastName, userCellNo, mailingAddress)
    }

    suspend fun isValidToken(token: String): UserLoginResponse {
        return suretiApi.isValidToken(token)
    }

    suspend fun getActiveChecks(token: String): ActiveChecksResponse {
        return suretiApi.getActiveChecks(token)
    }
}