package com.soenadiwai.tmdbmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soenadiwai.tmdbmobile.model.MovieDetail
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie_table where id = :id ")
    fun getMovieById(id: Int): MovieDetail

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieDetail: MovieDetail)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("UPDATE movie_table SET is_Favorite = :isFavorite WHERE id = :id")
    fun updateFavoriteFlag(isFavorite: Boolean,id: Int)

    @Query("SELECT is_Favorite FROM movie_table where id = :id ")
    fun isFavorite(id: Int): Boolean
}