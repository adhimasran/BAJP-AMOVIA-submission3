package com.adhi.amovia.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.vo.Resource

class DetailMovieViewModel(private val repo: FilmRepository) : ViewModel() {
    fun getDetailMovie(id: Int): LiveData<Resource<DetailMovieEntity>> =
        repo.getDetailMovie(id)

    fun setFavorite(movieEntity: DetailMovieEntity): Int {
        val newState = !movieEntity.favorite
        return repo.setFavoriteMovies(movieEntity, newState)
    }

    fun getFavorite(): LiveData<PagedList<DetailMovieEntity>> = repo.getFavoriteMovies()
}