package com.adhi.amovia.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.adhi.amovia.data.source.FilmDataSource
import com.adhi.amovia.data.source.NetworkBoundResource
import com.adhi.amovia.data.source.local.LocalDataSource
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity
import com.adhi.amovia.data.source.remote.ApiResponse
import com.adhi.amovia.data.source.remote.RemoteDataSource
import com.adhi.amovia.data.source.remote.response.DetailMovieResponse
import com.adhi.amovia.data.source.remote.response.DetailTvResponse
import com.adhi.amovia.data.source.remote.response.MovieResponse
import com.adhi.amovia.data.source.remote.response.TvResponse
import com.adhi.amovia.utils.AppExecutors
import com.adhi.amovia.vo.Resource

class FakeFilmRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FilmDataSource {
    override fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movies = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.imgPoster,
                        response.title,
                        response.rating
                    )
                    movies.add(movie)
                }
                localDataSource.insertMovies(movies)
            }
        }.asLiveData()
    }

    override fun getTvShows(sort: String): LiveData<Resource<PagedList<TvEntity>>> {
        return object :
        NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<TvResponse>) {
                val tvShows = ArrayList<TvEntity>()
                for (response in data) {
                    val tv = TvEntity(
                        response.id,
                        response.imgPoster,
                        response.name,
                        response.rating
                    )
                    tvShows.add(tv)
                }
                localDataSource.insertTvShows(tvShows)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(id: Int): LiveData<Resource<DetailMovieEntity>> {
        return object :
        NetworkBoundResource<DetailMovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailMovieEntity> =
                localDataSource.getMovie(id)

            override fun shouldFetch(data: DetailMovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override fun saveCallResult(data: DetailMovieResponse) {
                val genre = data.genres.joinToString { it.name }
                val movie = DetailMovieEntity(
                    data.id,
                    data.imgPoster,
                    data.imgBackdrop,
                    data.title,
                    data.tagline,
                    data.rating,
                    data.ratingCount,
                    data.release,
                    data.runtime,
                    data.overview,
                    genre
                )
                localDataSource.insertMovie(movie)
            }
        }.asLiveData()
    }

    override fun getDetailTv(id: Int): LiveData<Resource<DetailTvEntity>> {
        return object :
        NetworkBoundResource<DetailTvEntity, DetailTvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailTvEntity> =
                localDataSource.getTv(id)

            override fun shouldFetch(data: DetailTvEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<DetailTvResponse>> =
                remoteDataSource.getDetailTv(id)

            override fun saveCallResult(data: DetailTvResponse) {
                val runtime =
                    if (data.runtime.isNotEmpty()) data.runtime.first() else 0
                val genre = data.genres.joinToString { it.name }
                val tv = DetailTvEntity(
                    data.id,
                    data.imgPoster,
                    data.imgBackdrop,
                    data.name,
                    data.tagline,
                    data.rating,
                    data.ratingCount,
                    data.firstAir,
                    runtime,
                    data.overview,
                    genre
                )
                localDataSource.insertTv(tv)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<DetailMovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun setFavoriteMovies(movie: DetailMovieEntity, state: Boolean): Int {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
        return movie.id
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<DetailTvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun setFavoriteTvShows(tvShow: DetailTvEntity, state: Boolean): Int {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, state) }
        return tvShow.id
    }
}