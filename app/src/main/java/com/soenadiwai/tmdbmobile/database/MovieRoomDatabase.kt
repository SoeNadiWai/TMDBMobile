package com.soenadiwai.tmdbmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soenadiwai.tmdbmobile.model.MovieDetail
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader

@Database(entities = [MovieDetail::class,UpComingMovieHeader::class,PopularMovieHeader::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun upComingMovieDao(): UpComingMovieDao
    abstract fun popularMovieDao(): PopularMovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): MovieRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "tmdb_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}