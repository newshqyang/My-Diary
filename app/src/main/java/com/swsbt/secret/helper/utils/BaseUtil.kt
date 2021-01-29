package com.swsbt.secret.helper.utils

import android.app.Application
import android.content.Context

object BaseUtil {

    lateinit var context: Context
    fun init(app: Application) {
        context = app
    }

}