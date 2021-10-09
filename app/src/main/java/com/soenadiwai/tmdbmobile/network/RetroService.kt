package com.soenadiwai.tmdbmobile.network

import com.soenadiwai.tmdbmobile.model.MovieDetail
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {

    @GET("popular")
    fun getPopularMovieList (@Query("api_key") apiKey: String): Single<Response<PopularMovieHeader>>

    @GET("upcoming")
    fun getUpcomingMovieList (@Query("api_key") apiKey: String): Single<Response<UpComingMovieHeader>>

    @GET("{movie_id}")
    fun getMovieDetail (@Path("movie_id") id: Int?,@Query("api_key") apiKey: String): Single<Response<MovieDetail>>

}