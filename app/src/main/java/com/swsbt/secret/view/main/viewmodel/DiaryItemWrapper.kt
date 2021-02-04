package com.swsbt.secret.view.main.viewmodel

import com.swsbt.secret.helper.utils.DateUtil
import com.swsbt.secret.model.local.entity.DiaryEntity
import java.util.*

class DiaryItemWrapper {
    var title = item.title
    private val calendar by lazy {
        val cal = Calendar.getInstance()
        cal.timeInMillis = item.date
        cal
    }
    var day = "${calendar.get(Calendar.DAY_OF_MONTH)}"
    var month = DateUtil.monAbb(calendar)
    var time = DateUtil.time(calendar)
    private val ym by lazy {
        DateUtil.ym(calendar)
    }

    private var item = 
    constructor(item: DiaryEntity) {

    }
}