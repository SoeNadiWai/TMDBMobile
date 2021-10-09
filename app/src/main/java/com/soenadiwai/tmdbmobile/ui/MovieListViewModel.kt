package com.soenadiwai.tmdbmobile.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soenadiwai.tmdbmobile.database.MovieDetailDao
import com.soenadiwai.tmdbmobile.database.PopularMovieDao
import com.soenadiwai.tmdbmobile.database.UpComingMovieDao
import com.soenadiwai.tmdbmobile.model.Movie
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader
import com.soenadiwai.tmdbmobile.network.RetroInstance
import com.soenadiwai.tmdbmobile.network.RetroService
import com.soenadiwai.tmdbmobile.network.ServiceCallbackWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeViewModel(private val popularMovieDao: PopularMovieDao,private val upComingMovieDao: UpComingMovieDao) : ViewModel() {

    var popularMovieList: MutableLiveData<List<Movie>> = MutableLiveData()
    var upcomingMovieList: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getOfflinePopularMovieList(): PopularMovieHeader{
        return popularMovieDao.getPopularMovieHeader()
    }

    fun getOfflineUpcomingMovieList(): UpComingMovieHeader{
        return upComingMovieDao.getUpcomingMovieHeader()
    }

    fun insertPopularMovieHeader(popularMovieHeader: PopularMovieHeader){
        popularMovieDao.insert(popularMovieHeader)
    }

    fun insertUpcomingMovieHeader(upcomingMovieHeader: UpComingMovieHeader){
        upComingMovieDao.insert(upcomingMovieHeader)
    }

    fun deletePopularMovieHeader(){
        popularMovieDao.deleteAll()
    }

    fun deleteUpComingMovieHeader(){
        upComingMovieDao.deleteAll()
    }

    fun getPopularMovieList(serviceCallback: ServiceCallbackWrapper){
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            retroInstance.getPopularMovieList(RetroInstance.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response<PopularMovieHeader>>() {
                    override fun onSuccess(response: Response<PopularMovieHeader>) {
                        if (response.isSuccessful) {
                            serviceCallback.onSuccess(response.body())
                        } else {
                            serviceCallback.onFailure(response.code())
                        }
                        serviceCallback.onCompleted()
                    }

                    override fun onError(e: Throwable) {
                        serviceCallback.onError(e)
                    }
                })


    }

    fun getUpcomingMovieList(serviceCallback: ServiceCallbackWrapper){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getUpcomingMovieList(RetroInstance.apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<UpComingMovieHeader>>() {
                override fun onSuccess(response: Response<UpComingMovieHeader>) {
                    if (response.isSuccessful) {
                        serviceCallback.onSuccess(response.body())
                    } else {
                        serviceCallback.onFailure(response.code())
                    }
                    serviceCallback.onCompleted()
                }

                override fun onError(e: Throwable) {
                    serviceCallback.onError(e)
                }
            })
    }
}

class HomeViewModelFactory (private val popularMovieDao: PopularMovieDao,private val upComingMovieDao: UpComingMovieDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(popularMovieDao,upComingMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
