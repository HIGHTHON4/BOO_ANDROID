package com.example.boo_android.data.request

import com.google.gson.annotations.SerializedName

data class SeeChatHistoryRequest(
    @SerializedName("reportId") val reportId: String,
)
