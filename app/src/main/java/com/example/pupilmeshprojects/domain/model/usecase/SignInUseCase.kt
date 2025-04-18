package com.example.pupilmeshprojects.domain.model.usecase

import com.example.pupilmeshprojects.domain.model.User
import com.example.pupilmeshprojects.domain.model.repository.UserRepository

class SignInUseCase(private val repository: UserRepository) {
    suspend fun execute(email: String, password: String): Boolean {
        val user = repository.getUserByEmail(email)
        return if (user == null) {
            repository.insertUser(User(email, password))
            true
        } else {
            user.password == password
        }
    }
}


