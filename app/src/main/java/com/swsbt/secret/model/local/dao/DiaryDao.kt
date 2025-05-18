package com.swsbt.secret.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swsbt.secret.model.local.entity.DiaryEntity

@Dao
interface DiaryDao {

    /* 插入、更新 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diary: DiaryEntity)

    /* 查询日记的数据 */
    @Query("SELECT * FROM diaries WHERE id = :id")
    suspend fun get(id: Int): DiaryEntity

    /* 删除某一篇日记 */
    @Query("DELETE FROM diaries WHERE id = :id")
    fun deleteData(id: Int)

    /* 清空数据表 */
    @Query("DELETE FROM diaries")
    fun clear()

    /* 分页读取日记表的快照 */
    @Query("SELECT * FROM diaries WHERE title LIKE '%'|| :key ||'%' OR content LIKE '%'|| :key ||'%' ORDER BY date DESC limit :pageSize offset :page * :pageSize")
    suspend fun snapshotData(key: String, page: Int, pageSize: Int): List<DiaryEntity>

}