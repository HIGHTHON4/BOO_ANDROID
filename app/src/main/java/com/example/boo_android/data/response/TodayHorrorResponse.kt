package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TodayHorrorResponse(
    @SerializedName("title") val title: String,
    @SerializedName("reportId") val reportId: UUID,
)
