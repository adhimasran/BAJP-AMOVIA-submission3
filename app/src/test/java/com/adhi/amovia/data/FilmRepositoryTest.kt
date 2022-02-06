package com.adhi.amovia.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.adhi.amovia.data.source.local.LocalDataSource
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.data.source.remote.RemoteDataSource
import com.adhi.amovia.utils.AppExecutors
import com.adhi.amovia.utils.DataDummy
import com.adhi.amovia.utils.LiveDataTestUtil
import com.adhi.amovia.utils.PagedListUtil
import com.adhi.amovia.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class FilmRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val testExecutors: AppExecutors = AppExecutors(TestExecutor(), TestExecutor())

    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val sort = "Default"

    private val movies = DataDummy.dummyMovies()
    private val movie = DataDummy.dummyMovie()
    private val movieId = movie.id

    private val tvShows = DataDummy.dummyTvShows()
    private val tv = DataDummy.dummyTv()
    private val tvId = tv.id

    @Test
    fun getMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies(sort)).thenReturn(dataSourceFactory)
        filmRepository.getMovies(sort)

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(movies))
        verify(local).getMovies(sort)
        assertNotNull(movieEntities.data)
        assertEquals(movies.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        val dummyMovie = MutableLiveData<DetailMovieEntity>()
        dummyMovie.value = movie
        `when`(local.getMovie(movieId)).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(filmRepository.getDetailMovie(movieId))
        verify(local).getMovie(movieId)
        assertNotNull(movieEntity)
        assertEquals(movie.id, movieEntity.data?.id)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getTvShows(sort)).thenReturn(dataSourceFactory)
        filmRepository.getTvShows(sort)

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(tvShows))
        verify(local).getTvShows(sort)
        assertNotNull(tvEntities)
        assertEquals(tvShows.size, tvEntities.data?.size)
    }

    @Test
    fun getTv() {
        val dummyTv = MutableLiveData<DetailTvEntity>()
        dummyTv.value = tv
        `when`(local.getTv(tvId)).thenReturn(dummyTv)

        val tvEntity = LiveDataTestUtil.getValue(filmRepository.getDetailTv(tvId))
        verify(local).getTv(tvId)
        assertNotNull(tvEntity)
        assertEquals(tv.id, tvEntity.data?.id)
    }

    @Test
    fun setFavoriteMovies() {
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())

        doNothing().`when`(local).setFavoriteMovie(movie, true)
        filmRepository.setFavoriteMovies(movie, true)
        verify(local, times(1)).setFavoriteMovie(movie, true)

        doNothing().`when`(local).setFavoriteMovie(movie, false)
        filmRepository.setFavoriteMovies(movie, false)
        verify(local, times(1)).setFavoriteMovie(movie, false)
    }

    @Test
    fun setFavoriteTvShow() {
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        
        doNothing().`when`(local).setFavoriteTvShow(tv, true)
        filmRepository.setFavoriteTvShows(tv, true)
        verify(local).setFavoriteTvShow(tv, true)

        doNothing().`when`(local).setFavoriteTvShow(tv, false)
        filmRepository.setFavoriteTvShows(tv, false)
        verify(local).setFavoriteTvShow(tv, false)
    }
}