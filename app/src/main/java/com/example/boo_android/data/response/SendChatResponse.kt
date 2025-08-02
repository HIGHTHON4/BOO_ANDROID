package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName

data class SendChatResponse(
    @SerializedName("content") val content: String
)