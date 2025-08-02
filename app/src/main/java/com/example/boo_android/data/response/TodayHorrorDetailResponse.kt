package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName

data class TodayHorrorDetail(
    @SerializedName("text") val text: String,
    @SerializedName("content") val content: String,
    @SerializedName("title") val title: String,
)
