package com.soenadiwai.tmdbmobile.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityUtil {

    companion object{
        fun isConnected(context: Context?): Boolean {
            val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}