package com.adhi.amovia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.di.Injection
import com.adhi.amovia.ui.detail.DetailMovieViewModel
import com.adhi.amovia.ui.detail.DetailTvViewModel
import com.adhi.amovia.ui.movie.MovieViewModel
import com.adhi.amovia.ui.tv.TvShowViewModel

class ViewModelFactory private constructor(private val mFilmRepository: FilmRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                mFilmRepository
            ) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                mFilmRepository
            ) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(
                mFilmRepository
            ) as T
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> DetailTvViewModel(
                mFilmRepository
            ) as T
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
    }
}