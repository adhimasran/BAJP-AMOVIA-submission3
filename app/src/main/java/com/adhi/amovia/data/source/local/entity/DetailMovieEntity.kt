package com.adhi.amovia.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class DetailMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "poster_path")
    val imgPoster: String,

    @ColumnInfo(name = "backdrop_path")
    val imgBackdrop: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "tagline")
    val tagline: String,

    @ColumnInfo(name = "vote_average")
    val rating: Double,

    @ColumnInfo(name = "vote_count")
    val ratingCount: Int,

    @ColumnInfo(name = "release_date")
    val release: String,

    @ColumnInfo(name = "runtime")
    val runtime: Int,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
