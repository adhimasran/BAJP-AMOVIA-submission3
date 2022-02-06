package com.adhi.amovia.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.TvEntity
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel
    private val sort = "Default"
    private val data = MutableLiveData<Resource<PagedList<TvEntity>>>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repo)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(10)
        data.value = dummyTvShows

        `when`(repo.getTvShows(sort)).thenReturn(data)

        val tvShows = viewModel.getTvShows(sort).value?.data
        verify(repo).getTvShows(sort)
        assertNotNull(tvShows)
        assertEquals(10, tvShows?.size)

        viewModel.getTvShows(sort).observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }

    @Test
    fun getTvShowEmpty() {
        val dummyTvShows = Resource.success(pagedList)
        `when`(dummyTvShows.data?.size).thenReturn(0)
        data.value = dummyTvShows

        `when`(repo.getTvShows(sort)).thenReturn(data)
        val tvShows = viewModel.getTvShows(sort).value?.data
        verify(repo).getTvShows(sort)
        assertNotNull(tvShows)
        assertEquals(0, tvShows?.size)
    }

    @Test
    fun getTvShowsError() {
        val dummyTvShows = Resource.error(null, pagedList)
        data.value = dummyTvShows

        `when`(repo.getTvShows(sort)).thenReturn(data)
        val tvShows = viewModel.getTvShows(sort).value
        assertEquals(dummyTvShows.msg, tvShows?.msg)
    }
}