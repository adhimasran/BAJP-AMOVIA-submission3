package com.adhi.amovia.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.data.source.local.room.FilmDao
import com.adhi.amovia.utils.SortUtils
import com.adhi.amovia.utils.SortUtils.MOVIES
import com.adhi.amovia.utils.SortUtils.TV_SHOW

class LocalDataSource private constructor(private val dao: FilmDao) {
    fun getMovies(sort: String): DataSource.Factory<Int, MovieEntity> =
        dao.getSortMovies(SortUtils.getSortedFilm(sort, MOVIES))


    fun insertMovies(movies: List<MovieEntity>) = dao.insertMovies(movies)

    fun getMovie(id: Int): LiveData<DetailMovieEntity> = dao.getMovie(id)

    fun insertMovie(movie: DetailMovieEntity) = dao.insertMovie(movie)

    fun getTvShows(sort: String): DataSource.Factory<Int, TvEntity> =
        dao.getSortTvShows(SortUtils.getSortedFilm(sort, TV_SHOW))

    fun insertTvShows(tvShows: List<TvEntity>) = dao.insertTvShows(tvShows)

    fun getTv(id: Int): LiveData<DetailTvEntity> = dao.getTv(id)

    fun insertTv(tv: DetailTvEntity) = dao.insertTv(tv)

    fun setFavoriteMovie(movie: DetailMovieEntity, newState: Boolean) {
        movie.favorite = newState
        dao.updateMovie(movie)
    }

    fun getFavoriteMovie(): DataSource.Factory<Int, DetailMovieEntity> = dao.getFavMovies()

    fun setFavoriteTvShow(tv: DetailTvEntity, newState: Boolean) {
        tv.favorite = newState
        dao.updateTv(tv)
    }

    fun getFavoriteTvShow(): DataSource.Factory<Int, DetailTvEntity> = dao.getFavTvShows()

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }
}