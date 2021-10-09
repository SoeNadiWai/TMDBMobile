package com.soenadiwai.tmdbmobile.util

class AppConstant {

    companion object{
        var baseURL = "https://api.themoviedb.org/3/movie/"
        var baseImageURL = "http://image.tmdb.org/t/p/w500"
        var POPULAR_TYPE = "popular"
        var UPCOMING_TYPE = "upcoming"
        val RESPONSE_ALLOWED = 200
        val RESPONSE_BLOCKED = 403
        val RESPONSE_FULL = 423
        val RESPONSE_NOT_FOUND = 404
    }

}