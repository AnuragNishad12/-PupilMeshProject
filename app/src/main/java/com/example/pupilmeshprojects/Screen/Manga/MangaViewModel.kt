package com.example.pupilmeshprojects.Screen.Manga

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.pupilmeshprojects.Screen.RoomDb.MangaDatabase
import com.example.pupilmeshprojects.Screen.RoomDb.MangaEntity

class MangaViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val db = Room.databaseBuilder(context, MangaDatabase::class.java, "manga_db").build()
    private val dao = db.mangaDao()

    private val _mangaList = mutableStateOf<List<MangaItem>>(emptyList())
    val mangaList: State<List<MangaItem>> = _mangaList

    init {
        fetchManga()
    }

    fun fetchManga() {
        viewModelScope.launch {
            if (isInternetAvailable()) {
                try {
                    val api = getRetrofitWithHeader()
                    val response = api.fetchManga(1, "Harem,Fantasy")

                    if (response.code == 200) {
                        val items = response.data
                        _mangaList.value = items


                        dao.clearAll()
                        dao.insertAll(items.map { MangaEntity.fromMangaItem(it) })
                    } else {
                        loadFromDb()
                    }
                } catch (e: Exception) {
                    loadFromDb()
                }
            } else {
                loadFromDb()
            }
        }
    }

    private suspend fun loadFromDb() {
        _mangaList.value = dao.getAllManga().map { it.toMangaItem() }
    }

    private fun isInternetAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}

