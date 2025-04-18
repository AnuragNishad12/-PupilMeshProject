package com.example.pupilmeshprojects.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pupilmeshprojects.SessionManager
import com.example.pupilmeshprojects.domain.model.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isSignedIn by mutableStateOf(false)

    fun onSignInClick() {
        viewModelScope.launch {
            val result = signInUseCase.execute(email, password)
            if (result) {
                sessionManager.saveUser(email)
                isSignedIn = true
            }
        }
    }
}
