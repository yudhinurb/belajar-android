package com.yudhinurb.zwallet.ui.layout.main.findreceiver

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.AllContacts
import com.yudhinurb.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private var dataSource: ZWalletDataSource): ViewModel(){
    fun getAllContacts(): LiveData<Resource<APIResponse<List<AllContacts>>?>> {
        return dataSource.getAllContacts()
    }
}