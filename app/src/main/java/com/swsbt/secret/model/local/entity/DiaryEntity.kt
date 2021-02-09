package com.swsbt.secret.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @param picturePaths  图片地址，以英文逗号分隔
 */

@Entity(tableName = "diaries")
data class DiaryEntity(var title: String, var content: String, var date: Long, var picturePaths: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}