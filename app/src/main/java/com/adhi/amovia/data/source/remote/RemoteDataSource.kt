package com.adhi.amovia.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adhi.amovia.data.api.ApiConfig
import com.adhi.amovia.data.source.remote.response.*
import com.adhi.amovia.utils.Constants.CONVERSION_ERROR
import com.adhi.amovia.utils.Constants.NETWORK_FAILURE
import com.adhi.amovia.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    private val apiConfig = ApiConfig.getApiService()

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val listMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val client = apiConfig.getPopularMovies()
        client.enqueue(object : Callback<ListMovie> {
            override fun onResponse(
                call: Call<ListMovie>,
                response: Response<ListMovie>
            ) {
                if (response.isSuccessful) {
                    listMovie.value =
                        response.body()?.let { ApiResponse.success(it.results) }
                    EspressoIdlingResource.decrement()
                } else ApiResponse.error(CONVERSION_ERROR, null)
            }

            override fun onFailure(call: Call<ListMovie>, t: Throwable) {
                ApiResponse.error(NETWORK_FAILURE, null)
            }
        })

        return listMovie
    }

    fun getDetailMovie(id: Int): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()
        val movie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        val client = apiConfig.getDetailMovie(id)
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    movie.value =
                        response.body()?.let { ApiResponse.success(it) }
                    EspressoIdlingResource.decrement()
                } else ApiResponse.error(CONVERSION_ERROR, null)
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                ApiResponse.error(NETWORK_FAILURE, null)
            }
        })

        return movie
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvResponse>>> {
        EspressoIdlingResource.increment()
        val listTv = MutableLiveData<ApiResponse<List<TvResponse>>>()
        val client = apiConfig.getPopularTv()
        client.enqueue(object : Callback<ListTv> {
            override fun onResponse(
                call: Call<ListTv>,
                response: Response<ListTv>
            ) {
                if (response.isSuccessful) {
                    listTv.value =
                        response.body()?.results?.let { ApiResponse.success(it) }
                    EspressoIdlingResource.decrement()
                } else ApiResponse.error(CONVERSION_ERROR, null)
            }

            override fun onFailure(call: Call<ListTv>, t: Throwable) {
                ApiResponse.error(NETWORK_FAILURE, null)
            }
        })

        return listTv
    }

    fun getDetailTv(id: Int): LiveData<ApiResponse<DetailTvResponse>> {
        EspressoIdlingResource.increment()
        val tv = MutableLiveData<ApiResponse<DetailTvResponse>>()
        val client = apiConfig.getDetailTv(id)
        client.enqueue(object : Callback<DetailTvResponse> {
            override fun onResponse(
                call: Call<DetailTvResponse>,
                response: Response<DetailTvResponse>
            ) {
                if (response.isSuccessful) {
                    tv.value =
                        response.body()?.let { ApiResponse.success(it) }
                    EspressoIdlingResource.decrement()
                } else ApiResponse.error(CONVERSION_ERROR, null)
            }

            override fun onFailure(call: Call<DetailTvResponse>, t: Throwable) {
                ApiResponse.error(NETWORK_FAILURE, null)
            }
        })

        return tv
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
}