package com.yudhinurb.zwallet.model.request

data class ChangePasswordRequest(
    val old_password: String,
    val new_password: String
)
