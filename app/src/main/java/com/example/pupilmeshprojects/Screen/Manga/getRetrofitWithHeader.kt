package com.example.pupilmeshprojects.Screen.Manga

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofitWithHeader(): MangaApiService {
    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Rapidapi-Host", "mangaverse-api.p.rapidapi.com")
                .addHeader("X-Rapidapi-Key", "df7fc35342msh549c3a7200c6e21p10bb7bjsnedf571572b51") // replace this
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://mangaverse-api.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    return retrofit.create(MangaApiService::class.java)
}
