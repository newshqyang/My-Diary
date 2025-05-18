package com.swsbt.secret.model.repository

import com.swsbt.secret.model.local.dao.DiaryDao
import com.swsbt.secret.model.local.entity.DiaryEntity

class DiaryRepository(private val local: DiaryDao) {

    fun insert(diary: DiaryEntity) = local.insert(diary)

    suspend fun snapshotData(key: String, page: Int, pageSize: Int) = local.snapshotData(key, page, pageSize)

    suspend fun get(id: Int) = local.get(id)
}