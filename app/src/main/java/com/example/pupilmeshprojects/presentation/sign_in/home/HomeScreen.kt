package com.example.pupilmeshprojects.presentation.sign_in.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pupilmeshprojects.Screen.FaceRecognitionScreen
import com.example.pupilmeshprojects.Screen.Manga.MangaScreen


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "manga",
                    onClick = {
                        navController.navigate("manga") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Face, contentDescription = null) },
                    label = { Text("Manga Screen") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "face_recognition",
                    onClick = {
                        navController.navigate("face_recognition") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Face, contentDescription = null) },
                    label = { Text("Face Recognition Screen") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "manga",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("manga") {
                MangaScreen() // ✅ This is now visible
            }
            composable("face_recognition") {
                FaceRecognitionScreen()
            }
        }
    }
}

