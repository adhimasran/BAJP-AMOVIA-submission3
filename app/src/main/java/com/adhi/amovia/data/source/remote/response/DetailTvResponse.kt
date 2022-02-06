package com.adhi.amovia.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val imgPoster: String,

    @SerializedName("backdrop_path")
    val imgBackdrop: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("first_air_date")
    val firstAir: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("vote_average")
    val rating: Double,

    @SerializedName("vote_count")
    val ratingCount: Int,

    @SerializedName("episode_run_time")
    val runtime: List<Int>,

    @SerializedName("overview")
    val overview: String,

    @field:SerializedName("genres")
    var genres: List<GenreItems>
)
