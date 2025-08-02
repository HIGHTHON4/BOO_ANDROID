package com.example.boo_android.data.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private const val BASE_URL = "http://10.10.6.21:8080"

    private lateinit var sharedPreferences: SharedPreferences

    lateinit var chatApi: ChatApi
    lateinit var authApi: AuthApi
    lateinit var horrorApi: HorrorApi

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
        Log.d("ApiProvider", "SharedPreferences initialized.")
        val retrofit = getRetrofit()
        chatApi = retrofit.create(ChatApi::class.java)
        authApi = retrofit.create(AuthApi::class.java)
        horrorApi = retrofit.create(HorrorApi::class.java)
    }


    fun saveToken(token: String) {
        sharedPreferences.edit { putString("token", token) }
        Log.d("ApiProvider", "Token saved: $token")
    }

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(getLoggingInterceptor())
                    .addInterceptor(getTokenInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = sharedPreferences.getString("token", "") ?: ""
            Log.d("ApiProvider", "Retrieved token for interceptor: $token")
            val request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
                .build()
            chain.proceed(request)
        }
    }
}