package com.yudhinurb.zwallet.ui.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.Balance
import com.yudhinurb.zwallet.model.Invoice
import com.yudhinurb.zwallet.model.Profile
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.utils.Resource

class HomeViewModel(app: Application): ViewModel() {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)


    fun getInvoice(): LiveData<Resource<APIResponse<List<Invoice>>?>> {
        return dataSource.getInvoice()
    }
    fun getBalance(): LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }
    fun getProfile(): LiveData<Resource<APIResponse<Profile>?>> {
        return dataSource.getProfile()
    }
}