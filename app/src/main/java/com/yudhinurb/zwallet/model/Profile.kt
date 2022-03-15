package com.yudhinurb.zwallet.model


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("phone")
    val phone: String?
)