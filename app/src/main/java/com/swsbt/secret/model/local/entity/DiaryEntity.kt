package com.swsbt.secret.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "diaries", primaryKeys = ["id"])
data class DiaryEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val date: Date,
    val picturePaths: String
)