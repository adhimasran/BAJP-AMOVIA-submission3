package com.adhi.amovia.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "poster_path")
    val imgPoster: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "vote_average")
    val rating: Double
)