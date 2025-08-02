package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName

data class MyReportResponse(
    @SerializedName("level") val level: String,
    @SerializedName("date") val date: String,
    @SerializedName("title") val title: String,
)