package com.adhi.amovia.data.api

import com.adhi.amovia.data.source.remote.response.DetailMovieResponse
import com.adhi.amovia.data.source.remote.response.DetailTvResponse
import com.adhi.amovia.data.source.remote.response.ListMovie
import com.adhi.amovia.data.source.remote.response.ListTv
import com.adhi.amovia.utils.Constants.key
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=$key")
    fun getPopularMovies(): Call<ListMovie>

    @GET("tv/popular?api_key=$key")
    fun getPopularTv(): Call<ListTv>

    @GET("movie/{id}?api_key=$key")
    fun getDetailMovie(@Path("id") id: Int): Call<DetailMovieResponse>

    @GET("tv/{id}?api_key=$key")
    fun getDetailTv(@Path("id") id: Int): Call<DetailTvResponse>
}