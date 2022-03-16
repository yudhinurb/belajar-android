package com.yudhinurb.zwallet.ui.auth.createpin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.utils.Resource
import retrofit2.Call

class CreatePinViewModel(app: Application): ViewModel() {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)

    fun createPin(pin: Int): LiveData<Resource<APIResponse<String>?>> {
        return dataSource.setPin(pin)
    }
}