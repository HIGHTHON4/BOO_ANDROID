package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class StopChatResponse(
    @SerializedName("summary") val summary: UUID,
    @SerializedName("fearLevel") val fearLevel: String,
    @SerializedName("title") val title: String
)