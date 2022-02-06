package com.adhi.amovia.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("id")
    val id: Int,

    @field:SerializedName("poster_path")
    val imgPoster: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("vote_average")
    val rating: Double
)

data class ListTv(
    @field:SerializedName("results")
    val results: List<TvResponse>
)