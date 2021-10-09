package com.soenadiwai.tmdbmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader

@Dao
interface UpComingMovieDao {

    @Query("SELECT * FROM upcomingmovie_table")
    fun getUpcomingMovieHeader(): UpComingMovieHeader

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(upcomingMovieHeader: UpComingMovieHeader)

    @Query("DELETE FROM upcomingmovie_table")
    fun deleteAll()
}