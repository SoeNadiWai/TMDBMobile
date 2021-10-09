package com.soenadiwai.tmdbmobile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soenadiwai.tmdbmobile.database.MovieDetailDao
import com.soenadiwai.tmdbmobile.model.MovieDetail
import com.soenadiwai.tmdbmobile.network.RetroInstance
import com.soenadiwai.tmdbmobile.network.RetroService
import com.soenadiwai.tmdbmobile.network.ServiceCallbackWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MovieDetailViewModel(private val movieDetailDao: MovieDetailDao) : ViewModel() {

    fun getOfflineMovieDetail(id: Int): MovieDetail{
        return movieDetailDao.getMovieById(id)
    }

    fun updateFavorite(isFavorite: Boolean,id: Int){
        movieDetailDao.updateFavoriteFlag(isFavorite,id)
    }

    fun isFavorite(id: Int): Boolean {
        return movieDetailDao.isFavorite(id)
    }

    fun getMovieDetail(id: Int?,serviceCallback: ServiceCallbackWrapper){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getMovieDetail(id,RetroInstance.apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<MovieDetail>>() {
                override fun onSuccess(response: Response<MovieDetail>) {
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

     fun insert(movieDetail: MovieDetail){
        movieDetailDao.insert(movieDetail)
    }

}

class MovieDetailViewFactory (private val movieDetailDao: MovieDetailDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieDetailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}