package com.example.boo_android.data.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class AiListResponse(
    @SerializedName("name") var name: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: UUID,
    @SerializedName("image") val image: String,
)
