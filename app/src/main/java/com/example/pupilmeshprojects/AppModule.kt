package com.example.pupilmeshprojects

import android.content.Context
import androidx.room.Room
import com.example.pupilmeshprojects.data.UserDao
import com.example.pupilmeshprojects.data.UserDatabase
import com.example.pupilmeshprojects.data.repository.UserRepositoryImpl
import com.example.pupilmeshprojects.domain.model.repository.UserRepository
import com.example.pupilmeshprojects.domain.model.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideYourRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: UserRepository): SignInUseCase {
        return SignInUseCase(repository)
    }
}
