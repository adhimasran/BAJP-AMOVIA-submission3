package com.adhi.amovia.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getSortMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovie(id: Int): LiveData<DetailMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: DetailMovieEntity)

    @Update
    fun updateMovie(movie: DetailMovieEntity): Int

    @Query("SELECT * FROM tv_shows")
    fun getTvShows(): DataSource.Factory<Int, TvEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getSortTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvEntity>)

    @Query("SELECT * FROM tv WHERE id=:id")
    fun getTv(id: Int): LiveData<DetailTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tv: DetailTvEntity)

    @Update
    fun updateTv(tv: DetailTvEntity): Int

    @Query("Select * FROM movie WHERE  favorite = 1")
    fun getFavMovies(): DataSource.Factory<Int, DetailMovieEntity>

    @Query("Select * FROM tv WHERE favorite = 1")
    fun getFavTvShows(): DataSource.Factory<Int, DetailTvEntity>
}