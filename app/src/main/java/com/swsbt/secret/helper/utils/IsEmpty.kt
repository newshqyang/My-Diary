package com.swsbt.secret.helper.utils

import android.text.Editable

object IsEmpty {

    fun check(t: Editable?): Boolean {
        if (t == null || t.trim().isEmpty()) {
            return true
        }
        return false
    }

}