package com.swsbt.secret.model.repository

import com.swsbt.secret.model.local.dao.DiaryDao
import com.swsbt.secret.model.local.entity.DiaryEntity

class DiaryRepository(private val local: DiaryDao) {

    fun insert(diary: DiaryEntity) = local.insert(diary)

    fun getData(title: String, content: String, page: Int, pageSize: Int) = local.getBaseData(title, content, page, pageSize)
}