package com.example.pupilmeshprojects

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pupilmeshprojects.presentation.sign_in.SignInScreen
import com.example.pupilmeshprojects.presentation.sign_in.SignInViewModel
import com.example.pupilmeshprojects.presentation.sign_in.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.runtime.getValue


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent(sessionManager)
        }
    }
}

@Composable
fun AppContent(sessionManager: SessionManager) {
    val signedInUser by sessionManager.signedInUser.collectAsState(initial = null)

    if (signedInUser != null) {
        HomeScreen()
    } else {
        val viewModel: SignInViewModel = hiltViewModel()
        SignInScreen(viewModel = viewModel) {

        }
    }
}


