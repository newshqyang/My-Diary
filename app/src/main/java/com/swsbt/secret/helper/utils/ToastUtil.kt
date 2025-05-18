package com.swsbt.secret.helper.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtil {

    private var mToast: Toast? = null

    /*
    居中
     */
    fun text(context: Context, strResId: Int) {
        val s = context.resources.getString(strResId)
        text(context, s)
    }

    fun text(context: Context, text: CharSequence) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    /*
    底部
     */
    fun bottom(context: Context, strResId: Int) {
        val s = context.resources.getString(strResId)
        bottom(context, s)
    }

    fun bottom(context: Context, text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}