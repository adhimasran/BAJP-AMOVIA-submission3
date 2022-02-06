package com.adhi.amovia.di

import android.content.Context
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.LocalDataSource
import com.adhi.amovia.data.source.local.room.FilmDatabase
import com.adhi.amovia.data.source.remote.RemoteDataSource
import com.adhi.amovia.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FilmRepository {
        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}