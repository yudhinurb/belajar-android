package com.yudhinurb.zwallet.ui.layout.auth.createpin

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
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class CreatePinViewModel@Inject constructor(private var dataSource : ZWalletDataSource): ViewModel() {

    fun createPin(pin: Int): LiveData<Resource<APIResponse<String>?>> {
        return dataSource.setPin(pin)
    }
}