package com.yudhinurb.zwallet.model.request

data class TransferRequest(
    var receiver: String,
    val amount: Int,
    val notes: String
)
