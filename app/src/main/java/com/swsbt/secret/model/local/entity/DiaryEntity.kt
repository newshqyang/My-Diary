package com.swsbt.secret.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diaries")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var content: String,
    var date: Long,
    var picturePaths: String
)