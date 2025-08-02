package com.example.boo_android.data.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class StopChatRequest(
    @SerializedName("reportId") val reportId: String
)