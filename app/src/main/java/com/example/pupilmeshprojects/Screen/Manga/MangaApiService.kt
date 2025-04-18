package com.example.pupilmeshprojects.Screen.Manga

import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApiService {
    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int,
        @Query("genres") genres: String
    ): MangaResponse
}

