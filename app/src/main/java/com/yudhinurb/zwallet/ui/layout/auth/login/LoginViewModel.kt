package com.yudhinurb.zwallet.ui.layout.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(private var dataSource : ZWalletDataSource): ViewModel() {

    fun login(email: String, password: String): LiveData<Resource<APIResponse<User>?>> {
        return dataSource.login(email, password)
    }
}