package com.yudhinurb.zwallet.ui.layout.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.model.*
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var dataSource : ZWalletDataSource): ViewModel() {


    fun getInvoice(): LiveData<Resource<APIResponse<List<Invoice>>?>> {
        return dataSource.getInvoice()
    }
    fun getBalance(): LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }
    fun getProfile(): LiveData<Resource<APIResponse<Profile>?>> {
        return dataSource.getProfile()
    }
    fun changePassword(old_password: String, new_password:String) : LiveData<Resource<APIResponse<String>?>> {
        return dataSource.changePassword(old_password,new_password)
    }
    fun changeInfo(phone: String) : LiveData<Resource<APIResponse<User>?>> {
        return dataSource.changeInfo(phone)
    }
}