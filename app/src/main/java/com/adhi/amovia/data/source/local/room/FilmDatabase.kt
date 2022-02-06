package com.adhi.amovia.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adhi.amovia.data.source.local.entity.DetailMovieEntity
import com.adhi.amovia.data.source.local.entity.DetailTvEntity
import com.adhi.amovia.data.source.local.entity.MovieEntity
import com.adhi.amovia.data.source.local.entity.TvEntity

@Database(
    entities = [
        MovieEntity::class,
        DetailMovieEntity::class,
        TvEntity::class,
        DetailTvEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {

        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getInstance(context: Context): FilmDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "AMOVIA.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}