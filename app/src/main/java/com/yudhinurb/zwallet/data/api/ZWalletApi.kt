package com.yudhinurb.zwallet.data.api

import com.yudhinurb.zwallet.model.*
import com.yudhinurb.zwallet.model.request.*
import retrofit2.Call
import retrofit2.http.*

interface ZWalletApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): APIResponse<User>

    @POST("auth/signup")
    suspend fun signup(@Body request: RegisterRequest): APIResponse<String>

    @GET("auth/activate/{email}/{otp}")
    suspend fun tokenActivation(@Path("email")email: String, @Path("otp") otp: String): APIResponse<String>

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
    suspend fun checkPin(@Path("pin") pin: Int): APIResponse<String>

    @GET("tranfer/contactUser")
    suspend fun getAllContacts(): APIResponse<List<AllContacts>>

    @POST("tranfer/newTranfer")
    suspend fun transfer(@Header("x-access-PIN") pin: Int, @Body request: TransferRequest): APIResponse<Transfer>

    @PATCH("user/changePassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): APIResponse<String>

    @PATCH("user/changeInfo")
    suspend fun changeInfo(@Body request: ChangeInfoRequest): APIResponse<String>

}