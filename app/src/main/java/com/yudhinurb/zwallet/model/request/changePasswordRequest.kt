package com.yudhinurb.zwallet.model.request

data class changePasswordRequest(
    val old_password: String,
    val new_password: String
)
