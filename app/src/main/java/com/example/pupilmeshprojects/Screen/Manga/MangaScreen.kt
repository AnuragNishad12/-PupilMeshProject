package com.example.pupilmeshprojects.Screen.Manga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

// State
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.State

// UI elements
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text

// Image loading (Coil)
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

// Modifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MangaScreen(viewModel: MangaViewModel = viewModel()) {
    val mangaList by viewModel.mangaList

    LazyColumn {
        items(mangaList) { manga ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = manga.title, fontWeight = FontWeight.Bold)
                    Text(text = manga.genres.joinToString())
                    Text(text = manga.summary.take(100) + "...")

                    AsyncImage(
                        model = manga.thumb,
                        contentDescription = manga.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

