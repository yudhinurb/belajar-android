package com.yudhinurb.zwallet.data.api

import com.yudhinurb.zwallet.model.*
import com.yudhinurb.zwallet.model.request.LoginRequest
import com.yudhinurb.zwallet.model.request.RefreshTokenRequest
import com.yudhinurb.zwallet.model.request.RegisterRequest
import com.yudhinurb.zwallet.model.request.SetPinRequest
import retrofit2.Call
import retrofit2.http.*

interface ZWalletApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): APIResponse<User>

    @POST("auth/signup")
    fun signup(@Body request: RegisterRequest): Call<APIResponse<String>>

    @GET("home/getBalance")
    suspend fun getBalance(): APIResponse<List<Balance>>

    @GET("home/getInvoice")
    suspend fun getInvoice(): APIResponse<List<Invoice>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<APIResponse<User>>

    @GET("user/myProfile")
    suspend fun getProfile(): APIResponse<Profile>

    @PATCH("auth/PIN")
    suspend fun setPin(@Body request: SetPinRequest): APIResponse<String>

    @GET("auth/checkPIN/{pin}")
    suspend fun checkPIN(@Path("pin") pin: Int): APIResponse<String>

    @GET("tranfer/contactUser")
    suspend fun getAllContacts(): APIResponse<List<AllContacts>>
}