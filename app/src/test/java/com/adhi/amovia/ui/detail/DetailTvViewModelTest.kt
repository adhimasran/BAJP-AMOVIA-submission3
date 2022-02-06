package com.adhi.amovia.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmRepository
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.utils.DataDummy
import com.adhi.amovia.vo.Resource
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
class DetailTvViewModelTest {
    private lateinit var viewModel: DetailTvViewModel
    private val dummyTv = DataDummy.dummyTv()
    private val id = dummyTv.id
    private val data = MutableLiveData<Resource<DetailTvEntity>>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<DetailTvEntity>>

    @Mock
    private lateinit var pagedList: PagedList<DetailTvEntity>

    @Before
    fun setUp() {
        viewModel = DetailTvViewModel(repo)
    }

    @Test
    fun getTv() {
        val tvEntity = Resource.success(dummyTv)
        data.value = tvEntity

        `when`(repo.getDetailTv(id)).thenReturn(data)
        val tv = viewModel.getDetailTv(id).value
        verify(repo).getDetailTv(id)
        assertNotNull(tv)

        viewModel.getDetailTv(id).observeForever(observer)
        verify(observer).onChanged(tvEntity)
    }

    @Test
    fun getFavoriteTvShows() {
        val tvShows = MutableLiveData<PagedList<DetailTvEntity>>()
        tvShows.value = pagedList

        `when`(repo.getFavoriteTvShows()).thenReturn(tvShows)
        val listTv = viewModel.getFavorite().value
        verify(repo).getFavoriteTvShows()
        assertNotNull(listTv)
    }

    @Test
    fun setFavoriteTvShpws() {
        `when`(viewModel.setFavorite(dummyTv)).thenReturn(id)

        dummyTv.favorite = true
        val addToFavorite = viewModel.setFavorite(dummyTv)
        assertNotNull(addToFavorite)

        dummyTv.favorite = false
        val removeFavorite = viewModel.setFavorite(dummyTv)
        assertNotNull(removeFavorite)
    }
}