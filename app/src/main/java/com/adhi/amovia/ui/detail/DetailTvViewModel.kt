package com.adhi.amovia.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.vo.Resource

class DetailTvViewModel(private val repo: FilmRepository) : ViewModel() {
    fun getDetailTv(id: Int): LiveData<Resource<DetailTvEntity>> =
        repo.getDetailTv(id)

    fun setFavorite(tvEntity: DetailTvEntity): Int {
        val newState = !tvEntity.favorite
        return repo.setFavoriteTvShows(tvEntity, newState)
    }

    fun getFavorite(): LiveData<PagedList<DetailTvEntity>> = repo.getFavoriteTvShows()
}