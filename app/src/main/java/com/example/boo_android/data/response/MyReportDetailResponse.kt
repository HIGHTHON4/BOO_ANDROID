package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class MyReportDetailResponse(
    @SerializedName("summary") val summary: String,
    @SerializedName("fearLevel") val fearLevel: String,
    @SerializedName("title") val title: String,
    @SerializedName("aiName") val aiName: String,
)