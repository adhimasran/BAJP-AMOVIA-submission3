package com.adhi.amovia.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreItems(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)
