package com.yudhinurb.zwallet.data.api

import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.Balance
import com.yudhinurb.zwallet.model.Invoice
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.model.request.LoginRequest
import com.yudhinurb.zwallet.model.request.RefreshTokenRequest
import com.yudhinurb.zwallet.model.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<APIResponse<User>>
    @POST("auth/signup")
    fun signup(@Body request: RegisterRequest): Call<APIResponse<String>>
    @GET("home/getBalance")
    fun getBalance(): Call<APIResponse<List<Balance>>>
    @GET("home/getInvoice")
    fun getInvoice(): Call<APIResponse<List<Invoice>>>
}