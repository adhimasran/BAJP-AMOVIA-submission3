package com.adhi.amovia.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel
    private val sort = "Default"
    private val data = MutableLiveData<Resource<PagedList<MovieEntity>>>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repo)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(10)
        data.value = dummyMovies

        `when`(repo.getMovies(sort)).thenReturn(data)
        val movies = viewModel.getMovies(sort).value?.data
        verify(repo).getMovies(sort)
        assertNotNull(movies)
        assertEquals(10, movies?.size)

        viewModel.getMovies(sort).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getMoviesEmpty() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(0)
        data.value = dummyMovies

        `when`(repo.getMovies(sort)).thenReturn(data)
        val movies = viewModel.getMovies(sort).value?.data
        verify(repo).getMovies(sort)
        assertNotNull(movies)
        assertEquals(0, movies?.size)
    }

    @Test
    fun getMoviesError() {
        val dummyMovies = Resource.error(null, pagedList)
        data.value = dummyMovies

        `when`(repo.getMovies(sort)).thenReturn(data)
        val movies = viewModel.getMovies(sort).value
        assertEquals(dummyMovies.msg, movies?.msg)
    }
}