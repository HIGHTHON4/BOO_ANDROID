package com.example.boo_android.data.api

import com.example.boo_android.data.response.TodayHorrorDetailResponse
import com.example.boo_android.data.response.TodayHorrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HorrorApi {
    @GET("/today-horror")
    suspend fun fetchTodayHorror(): List<TodayHorrorResponse>

    @GET("/today-horror/query")
    suspend fun fetchTodayHorrorDetail(
        @Query("reportId") reportId: String
    ): TodayHorrorDetailResponse
}