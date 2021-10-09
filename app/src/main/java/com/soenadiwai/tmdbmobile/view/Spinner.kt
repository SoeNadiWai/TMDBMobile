package com.soenadiwai.tmdbmobile.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kaopiz.kprogresshud.KProgressHUD
import com.soenadiwai.tmdbmobile.R

class Spinner() {

        private var hud: KProgressHUD? = null

        fun getSpinner(context: Context){
            val processDialog = View.inflate(context, R.layout.process_dialog_custom_layout, null)
            val imageView = processDialog.findViewById<ImageView>(R.id.image_view)
            val textView = processDialog.findViewById<TextView>(R.id.please_wait_text)
            imageView.setBackgroundResource(R.drawable.spin_animation)
            val drawable = imageView.background as AnimationDrawable
            drawable.start()
            hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCustomView(processDialog)
                .setDetailsLabel("")
                .setBackgroundColor(Color.parseColor("#66000000"))
                .setAnimationSpeed(1)
                .setCancellable(true)
        }

        fun show() {
            dismiss()
            hud?.show()
        }

        fun dismiss() {
            if (hud?.isShowing == true) {
                hud?.dismiss()
            }
        }


}