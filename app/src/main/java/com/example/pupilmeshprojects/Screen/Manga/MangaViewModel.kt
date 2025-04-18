package com.example.pupilmeshprojects.Screen.Manga

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class MangaViewModel : ViewModel() {

    private val _mangaList = mutableStateOf<List<MangaItem>>(emptyList()) // Initialize with an empty list
    val mangaList: State<List<MangaItem>> = _mangaList

    init {
        fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch {
            try {
                val api = getRetrofitWithHeader()
                val response = api.fetchManga(page = 1, genres = "Harem,Fantasy")

                // Ensure the response is valid
                if (response.code == 200) {
                    _mangaList.value = response.data // Update with the data from the 'data' field
                    Log.d("MangaViewModel", "Manga list size: ${response.data.size}")
                } else {
                    Log.e("MangaViewModel", "Error: ${response.code}")
                }

            } catch (e: Exception) {
                Log.e("MangaViewModel", "Error fetching manga: ${e.message}")
                _mangaList.value = emptyList() // Ensure you don't leave it null
            }
        }
    }
}

