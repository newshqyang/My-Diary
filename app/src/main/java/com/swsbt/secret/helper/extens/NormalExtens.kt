package com.swsbt.secret.helper.extens

import android.app.Activity
import android.content.Intent
import android.os.Bundle

//////////////////////////////Activity///////////////////////////////
fun Activity.navigateTo(clazz: Class<*>, bundle: Bundle? = null) {
    startActivity(Intent(this, clazz).apply {
        bundle?.let {
            putExtras(it)
        }
    })
}