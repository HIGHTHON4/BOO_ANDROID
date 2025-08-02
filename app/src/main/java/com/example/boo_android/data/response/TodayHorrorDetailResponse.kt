package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName

data class TodayHorrorDetailResponse(
    @SerializedName("text") val text: String,
    @SerializedName("content") val content: String,
    @SerializedName("title") val title: String,
)
