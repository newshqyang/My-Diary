package com.swsbt.secret.helper.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    private val mCalendar by lazy { Calendar.getInstance() }

    /* 获取月 */
    fun m(time: Long): Int {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        return m(c)
    }
    fun m(calendar: Calendar = mCalendar) = calendar.get(Calendar.MONTH) + 1

    @SuppressLint("SimpleDateFormat")
    fun str(date: Long): String {
        val sdf = SimpleDateFormat("yyyy/mm/dd HH:MM")
        return sdf.format(Date(date))
    }

}