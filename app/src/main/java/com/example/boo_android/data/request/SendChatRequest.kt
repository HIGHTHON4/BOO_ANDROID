package com.example.boo_android.data.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class SendChatRequest(
    @SerializedName("text") val text: String,
    @SerializedName("reportId") val reportId: String
)