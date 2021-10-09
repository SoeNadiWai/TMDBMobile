package com.soenadiwai.tmdbmobile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcomingmovie_table")
data class UpComingMovieHeader(
    @PrimaryKey @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "dates") val dates: Dates,
    @ColumnInfo(name = "results") val results: List<Movie>,
    @ColumnInfo(name = "total_pages") val total_pages: Int,
    @ColumnInfo(name = "total_results") val total_results: Int
)