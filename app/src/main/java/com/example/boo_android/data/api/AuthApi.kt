package com.example.boo_android.data.api

import com.example.boo_android.Sort
import com.example.boo_android.data.response.MyReportDetailResponse
import com.example.boo_android.data.response.MyReportResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface AuthApi {

    @GET("/report/my")
    suspend fun fetchMyReport(
        @Query("sort") sort: Sort,
        @Query("ai") ai: List<String>,
    ): List<MyReportResponse>

    @GET("/report")
    suspend fun fetchMyReportDetail(
        @Query("reportId") reportId: String,
    ): MyReportDetailResponse
}