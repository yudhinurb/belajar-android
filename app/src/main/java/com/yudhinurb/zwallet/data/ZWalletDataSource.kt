package com.yudhinurb.zwallet.data

import androidx.lifecycle.liveData
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.Balance
import com.yudhinurb.zwallet.model.Invoice
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.model.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class ZWalletDataSource(private val apiCLient: ZWalletApi) {
    fun login(email: String, password: String) = liveData<APIResponse<User>>(Dispatchers.IO) {
        try {
            val loginRequest = LoginRequest(email=email, password=password)
            val response = apiCLient.login(loginRequest)
            emit(response)
        } catch (e: Exception) {
            emit(APIResponse(400, e.localizedMessage, null))
        }

    }

    fun getInvoice() = liveData<APIResponse<List<Invoice>>>(Dispatchers.IO) {
        try {
            var response = apiCLient.getInvoice()
            emit(response)
        } catch (e: Exception){
            emit(APIResponse(400, e.localizedMessage, null))
        }
    }

    fun getBalance() = liveData<APIResponse<List<Balance>>>(Dispatchers.IO) {
        try {
            var response = apiCLient.getBalance()
            emit(response)
        } catch (e: Exception){
            emit(APIResponse(400, e.localizedMessage, null))
        }
    }
}