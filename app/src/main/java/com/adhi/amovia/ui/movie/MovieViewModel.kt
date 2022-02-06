package com.adhi.amovia.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.vo.Resource

class MovieViewModel(private val repo: FilmRepository) : ViewModel() {
    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> =
        repo.getMovies(sort)
}