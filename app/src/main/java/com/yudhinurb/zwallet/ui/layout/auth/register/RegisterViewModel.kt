package com.yudhinurb.zwallet.ui.layout.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private var dataSource : ZWalletDataSource): ViewModel() {
    private var otpEmail = MutableLiveData<String>()

    fun register(username: String, email: String, password:String): LiveData<Resource<APIResponse<String>?>> {
        return dataSource.register(username, email, password)
    }

    fun setEmail(email: String) {
        otpEmail.value = email
    }

    fun getEmail(): MutableLiveData<String> {
        return otpEmail
    }

    fun tokenActivation(email: String, otp:String): LiveData<Resource<APIResponse<String>?>>{
        return dataSource.tokenActivation(email, otp)
    }
}