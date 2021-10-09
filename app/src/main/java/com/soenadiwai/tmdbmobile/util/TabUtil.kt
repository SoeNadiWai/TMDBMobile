package com.soenadiwai.tmdbmobile.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.soenadiwai.tmdbmobile.R

class TabUtil {

    companion object{
        fun renderTabView(context: Context?, titleResource: String?): View? {
            val view =
                LayoutInflater.from(context).inflate(R.layout.tab_view, null) as FrameLayout
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            (view.findViewById<View>(R.id.tab_text) as TextView).text = titleResource
            return view
        }
    }



}