package com.swsbt.secret.view.main.viewmodel

import com.swsbt.secret.model.local.entity.DiaryEntity

class DiaryItemWrapper(item: DiaryEntity) {
    var title = item.title
    var date = item.date
}