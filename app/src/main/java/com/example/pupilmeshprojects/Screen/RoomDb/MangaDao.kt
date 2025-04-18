package com.example.pupilmeshprojects.Screen.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga_table")
    suspend fun getAllManga(): List<MangaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangas: List<MangaEntity>)

    @Query("DELETE FROM manga_table")
    suspend fun clearAll()
}
