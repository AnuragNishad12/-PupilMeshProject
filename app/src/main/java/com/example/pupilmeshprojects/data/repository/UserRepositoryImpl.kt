package com.example.pupilmeshprojects.data.repository

import com.example.pupilmeshprojects.data.UserDao
import com.example.pupilmeshprojects.data.UserEntity
import com.example.pupilmeshprojects.domain.model.User
import com.example.pupilmeshprojects.domain.model.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.let {
            User(it.email, it.password)
        }
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(UserEntity(user.email, user.password))
    }
}
