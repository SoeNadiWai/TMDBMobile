package com.soenadiwai.tmdbmobile.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.soenadiwai.tmdbmobile.model.*

class Converters {
    @TypeConverter
    fun listToJsonCompanyObject(value: List<ProductionCompany>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListCompanyObject(value: String) = Gson().fromJson(value, Array<ProductionCompany>::class.java).toList()

    @TypeConverter
    fun listToJsonCountryObject(value: List<ProductionCountry>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListCountryObject(value: String) = Gson().fromJson(value, Array<ProductionCountry>::class.java).toList()

    @TypeConverter
    fun listToJsonLanguageObject(value: List<SpokenLanguage>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListLanguageObject(value: String) = Gson().fromJson(value, Array<SpokenLanguage>::class.java).toList()

    @TypeConverter
    fun listToJsonGenreObject(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGenreObject(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()

    @TypeConverter
    fun listToJsonMovieObject(value: List<Movie>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListMovieObject(value: String) = Gson().fromJson(value, Array<Movie>::class.java).toList()

    @TypeConverter
    fun objectoJsonDatesObject(value: Dates?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToDatesObject(value: String) = Gson().fromJson(value, Dates::class.java)

}