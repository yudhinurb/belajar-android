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
import com.yudhinurb.zwallet.network.NetworkConfig

class HomeViewModel(app: Application): ViewModel() {
    private val invoices = MutableLiveData<List<Invoice>>()
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)


    fun getInvoice(): LiveData<APIResponse<List<Invoice>>>{
        return dataSource.getInvoice()
    }
    fun getBalance(): LiveData<APIResponse<List<Balance>>>{
        return dataSource.getBalance()
    }
}