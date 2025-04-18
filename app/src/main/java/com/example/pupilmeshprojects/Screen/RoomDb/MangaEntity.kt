package com.example.pupilmeshprojects.Screen.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pupilmeshprojects.Screen.Manga.MangaItem

@Entity(tableName = "manga_table")
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val sub_title: String,
    val status: String,
    val thumb: String,
    val summary: String,
    val authors: String,
    val genres: String,
    val nsfw: Boolean,
    val type: String,
    val total_chapter: Int,
    val create_at: Long,
    val update_at: Long
) {
    fun toMangaItem(): MangaItem = MangaItem(
        id, title, sub_title, status, thumb, summary,
        authors.split(","), genres.split(","), nsfw, type, total_chapter, create_at, update_at
    )

    companion object {
        fun fromMangaItem(item: MangaItem) = MangaEntity(
            item.id, item.title, item.sub_title, item.status, item.thumb, item.summary,
            item.authors.joinToString(","), item.genres.joinToString(","),
            item.nsfw, item.type, item.total_chapter, item.create_at, item.update_at
        )
    }
}
