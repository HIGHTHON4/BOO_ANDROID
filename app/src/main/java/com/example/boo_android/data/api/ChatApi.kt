package com.example.boo_android.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import com.example.boo_android.data.request.SendChatRequest
import com.example.boo_android.data.request.StartChatRequest
import com.example.boo_android.data.request.StopChatRequest
import com.example.boo_android.data.response.AiListResponse
import com.example.boo_android.data.response.SendChatResponse
import com.example.boo_android.data.response.StopChatResponse
import java.util.UUID

interface ChatApi {
    @POST("/chat")
    suspend fun sendChat(
        @Body request: SendChatRequest
    ): SendChatResponse

    @POST("/chat/start")
    suspend fun startChat(
        @Body request: StartChatRequest
    ): UUID

    @POST("/chat/stop")
    suspend fun stopChat(
        @Body request: StopChatRequest
    ): StopChatResponse

    @GET("/ai")
    suspend fun fetchAiList(): List<AiListResponse>
}