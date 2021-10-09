package com.soenadiwai.tmdbmobile.network

import android.content.Context
import android.widget.Toast
import com.soenadiwai.tmdbmobile.view.Spinner
import com.soenadiwai.tmdbmobile.util.AppConstant

abstract class ServiceCallbackWrapper(context: Context?) {
    private var context: Context? = null
    protected open var spinner: Spinner? = null

    init {
        this.context = context
        this.spinner = Spinner()
        onStart()
    }


    open fun onStart() {}

    abstract fun <T> onSuccess(t: T)

    open fun onFailure(responseCode: Int) {
        context?.let { showServerError(responseCode, it) }
    }

    open fun onError(t: Throwable?) {
        Toast.makeText(context, "Cannot connect to Server", Toast.LENGTH_SHORT)
    }

    open fun onCompleted() {}

    open fun showServerError(responseCode: Int, context: Context) {
        when (responseCode) {
            AppConstant.RESPONSE_BLOCKED -> {
                Toast.makeText(this.context,"Connection is blocked!", Toast.LENGTH_SHORT).show()
            }
            AppConstant.RESPONSE_NOT_FOUND -> {
                Toast.makeText(this.context,"404 Not Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}