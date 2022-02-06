package com.adhi.amovia.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.vo.Resource

class TvShowViewModel(private val repo: FilmRepository) : ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvEntity>>> =
        repo.getTvShows(sort)
}