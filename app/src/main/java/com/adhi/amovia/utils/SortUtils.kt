package com.adhi.amovia.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val DEFAULT = "Default"
    const val RATING = "Best Rating"
    const val MOVIES = "movies"
    const val TV_SHOW = "tv_shows"

    fun getSortedFilm(filter: String, tableName: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $tableName ")
        when (filter) {
            DEFAULT -> StringBuilder().append("SELECT * FROM $tableName")
            RATING -> simpleQuery.append("ORDER BY vote_average DESC")
        }

        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}