package com.bucks.suretitestapp.api

import retrofit2.http.POST
import retrofit2.http.Query

interface SuretiApi {

    companion object {
        const val BASE_URL = "https://devconsole.sureti.com:9000/Mobile/"
    }

    @POST("doesUserExist")
    suspend fun getDoesUserExist(
        @Query("email") email: String
    ): DoesUserExistDto

    @POST("UserLogin")
    suspend fun getUserLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): UserLoginResponse

    @POST("RegisterUser")
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("userCellNo") userCellNo: String,
        @Query("mailingAddress") mailingAddress: String,
    ): UserLoginResponse

    @POST("isValidToken")
    suspend fun isValidToken(
        @Query("token") token: String
    ): UserLoginResponse

    @POST("getActiveChecks")
    suspend fun getActiveChecks(
        @Query("token") token: String
    ): ActiveChecksResponse
}