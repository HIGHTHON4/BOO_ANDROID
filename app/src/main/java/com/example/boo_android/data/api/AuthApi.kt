package com.example.boo_android.data.api

import com.example.boo_android.Sort
import com.example.boo_android.data.response.MyReportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("/report/my")
    fun fetchMyReport(
        @Query("sort") sort: Sort,
        @Query("ai") ai: Array<String>,
    ): List<MyReportResponse>
}