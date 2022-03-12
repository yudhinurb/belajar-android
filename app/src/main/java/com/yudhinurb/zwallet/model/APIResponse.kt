package com.yudhinurb.zwallet.model


// 200 success
// 400 error/failed
data class APIResponse<T>(
    var status: Int,
    var message: String,
    var data: T?
)
