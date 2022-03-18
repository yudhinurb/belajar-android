package com.yudhinurb.zwallet.ui.layout.main.findreceiver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yudhinurb.zwallet.data.ZWalletDataSource
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.AllContacts
import com.yudhinurb.zwallet.model.request.TransferRequest
import com.yudhinurb.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private var dataSource: ZWalletDataSource): ViewModel(){
    private var selectedContact = MutableLiveData<AllContacts>()
    private var transfer = MutableLiveData<TransferRequest>()

    fun getAllContacts(): LiveData<Resource<APIResponse<List<AllContacts>>?>> {
        return dataSource.getAllContacts()
    }

    fun setSelectedContact(contact: AllContacts) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): MutableLiveData<AllContacts> {
        return selectedContact
    }

    fun setAmount(data: TransferRequest){
        transfer.value = data
    }

    fun getAmount(): MutableLiveData<TransferRequest> {
        return transfer
    }
}