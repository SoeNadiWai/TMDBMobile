package com.soenadiwai.tmdbmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soenadiwai.tmdbmobile.model.MovieDetail
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader

@Dao
interface PopularMovieDao {

    @Query("SELECT * FROM popularmovie_table")
    fun getPopularMovieHeader(): PopularMovieHeader

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(popularMovieHeader: PopularMovieHeader)

    @Query("DELETE FROM popularmovie_table")
    fun deleteAll()

}