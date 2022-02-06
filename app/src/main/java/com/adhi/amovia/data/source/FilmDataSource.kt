package com.adhi.amovia.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.vo.Resource

interface FilmDataSource {
    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTvShows(sort: String): LiveData<Resource<PagedList<TvEntity>>>

    fun getDetailMovie(id: Int): LiveData<Resource<DetailMovieEntity>>

    fun getDetailTv(id: Int): LiveData<Resource<DetailTvEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<DetailMovieEntity>>

    fun setFavoriteMovies(movie: DetailMovieEntity, state: Boolean): Int

    fun getFavoriteTvShows(): LiveData<PagedList<DetailTvEntity>>

    fun setFavoriteTvShows(tvShow: DetailTvEntity, state: Boolean): Int
}