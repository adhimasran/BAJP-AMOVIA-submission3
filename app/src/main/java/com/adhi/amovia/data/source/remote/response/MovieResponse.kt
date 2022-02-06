package com.adhi.amovia.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("poster_path")
    val imgPoster: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("vote_average")
    val rating: Double
)

data class ListMovie(
    @field:SerializedName("results")
    val results: List<MovieResponse>
)
