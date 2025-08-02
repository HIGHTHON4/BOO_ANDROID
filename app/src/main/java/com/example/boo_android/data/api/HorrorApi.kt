package com.example.boo_android.data.api

import com.example.boo_android.data.response.TodayHorrorResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface HorrorApi {
    @GET("/today-horror")
    suspend fun fetchTodayHorror(): TodayHorrorResponse
}