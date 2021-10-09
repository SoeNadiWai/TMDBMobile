package com.soenadiwai.tmdbmobile.network

import com.soenadiwai.tmdbmobile.util.AppConstant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object{

        val apiKey = "81681a7a2958897396cc2064653021e1"

        fun getRetroInstance() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(AppConstant.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}