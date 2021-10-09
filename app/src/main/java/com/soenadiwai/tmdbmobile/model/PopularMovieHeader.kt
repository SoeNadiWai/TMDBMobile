package com.soenadiwai.tmdbmobile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularmovie_table")
data class PopularMovieHeader(
    @PrimaryKey @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "results") val results: List<Movie>,
    @ColumnInfo(name = "total_pages") val total_pages: Int,
    @ColumnInfo(name = "total_results") val total_results: Int
)