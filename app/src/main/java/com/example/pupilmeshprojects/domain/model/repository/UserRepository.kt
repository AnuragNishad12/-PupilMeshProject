package com.example.pupilmeshprojects.domain.model.repository

import com.example.pupilmeshprojects.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User)
}
