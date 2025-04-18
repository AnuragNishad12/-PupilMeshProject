package com.example.pupilmeshprojects.Screen.Manga

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://mangaverse-api.p.rapidapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: MangaApiService = retrofit.create(MangaApiService::class.java)
}
