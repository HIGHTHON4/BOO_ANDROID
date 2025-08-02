package com.example.boo_android.data.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class StopChatResponse(
    @SerializedName("reportId") val reportId: UUID
)