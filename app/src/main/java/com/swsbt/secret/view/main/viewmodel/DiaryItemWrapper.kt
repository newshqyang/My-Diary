package com.swsbt.secret.view.main.viewmodel

import com.swsbt.secret.helper.utils.old.DateUtil
import com.swsbt.secret.model.local.entity.DiaryEntity
import java.util.*

class DiaryItemWrapper(item: DiaryEntity, showYm: Boolean = false) {
    private val calendar by lazy {
        val cal = Calendar.getInstance()
        cal.timeInMillis = item.date
        cal
    }
    val day = "${calendar.get(Calendar.DAY_OF_MONTH)}"
    val month = DateUtil.monAbb(calendar)
    val time = DateUtil.time(calendar)
    val ym by lazy {
        DateUtil.ym(calendar)
    }
    val title = item.title

    val sym = showYm  //是否展示年份日期（月份间隔符）

    val id = item.id

}