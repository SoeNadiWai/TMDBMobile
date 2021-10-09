package com.soenadiwai.tmdbmobile

import android.app.Application
import com.soenadiwai.tmdbmobile.database.MovieRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { MovieRoomDatabase.getDatabase(this) }
    val moviedetail_repository by lazy {database.movieDetailDao()}
    val popularmovielist_repository by lazy { database.popularMovieDao() }
    val upcomingmovielist_repository by lazy { database.upComingMovieDao() }


}