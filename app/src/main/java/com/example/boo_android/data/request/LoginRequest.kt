package com.example.boo_android.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("accountId") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("deviceToken") val deviceToken: String,
    @SerializedName("os") val os: String,
)
