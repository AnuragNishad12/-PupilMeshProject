package com.example.pupilmeshprojects.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp



@Composable
fun SignInScreen(viewModel: SignInViewModel, onSignedIn: () -> Unit) {
    if (viewModel.isSignedIn) onSignedIn()

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = viewModel.email, onValueChange = { viewModel.email = it }, label = { Text("Email") })
        OutlinedTextField(value = viewModel.password, onValueChange = { viewModel.password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Button(onClick = viewModel::onSignInClick, Modifier.padding(top = 16.dp)) {
            Text("Sign In")
        }
    }
}


