package com.yudhinurb.zwallet.data

import androidx.lifecycle.liveData
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.Balance
import com.yudhinurb.zwallet.model.Invoice
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.model.request.LoginRequest
import com.yudhinurb.zwallet.model.request.SetPinRequest
import com.yudhinurb.zwallet.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject


class ZWalletDataSource @Inject constructor(private val apiCLient: ZWalletApi) {
    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val loginRequest = LoginRequest(email=email, password=password)
            val response = apiCLient.login(loginRequest)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage))
        }

    }

    fun getInvoice() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var response = apiCLient.getInvoice()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getBalance() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var response = apiCLient.getBalance()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getProfile() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var response = apiCLient.getProfile()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun setPin(pin: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val setPinRequest = SetPinRequest(PIN = pin)
            var response = apiCLient.setPin(setPinRequest)
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }

    fun getAllContacts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var response = apiCLient.getAllContacts()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }
}