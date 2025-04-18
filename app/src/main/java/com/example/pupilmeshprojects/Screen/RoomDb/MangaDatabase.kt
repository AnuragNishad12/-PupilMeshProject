package com.example.pupilmeshprojects.Screen.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MangaEntity::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
}
