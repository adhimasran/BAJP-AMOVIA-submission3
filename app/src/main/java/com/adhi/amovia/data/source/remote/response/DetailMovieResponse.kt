package com.adhi.amovia.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val imgPoster: String,

    @SerializedName("backdrop_path")
    val imgBackdrop: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("vote_average")
    val rating: Double,

    @SerializedName("vote_count")
    val ratingCount: Int,

    @SerializedName("release_date")
    val release: String,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("overview")
    val overview: String,

    @field:SerializedName("genres")
    var genres: List<GenreItems>
)
