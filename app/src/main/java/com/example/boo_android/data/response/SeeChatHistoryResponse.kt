package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName

data class SeeChatHistoryResponse(
    @SerializedName("sender") val sender: String,
    @SerializedName("content") val content: String,
)
