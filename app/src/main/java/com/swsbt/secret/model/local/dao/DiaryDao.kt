package com.swsbt.secret.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swsbt.secret.model.local.entity.DiaryEntity
import io.reactivex.Single

@Dao
interface DiaryDao {

    /* 插入、更新 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diary: DiaryEntity)

    /* 模糊查询、分页查询所有日记的基本数据 */
    @Query("SELECT * FROM diaries WHERE title LIKE '%'|| :title ||'%' OR content LIKE '%'|| :content ||'%' limit :page offset :page * :pageSize")
    fun getBaseData(title: String, content:String, page: Int, pageSize: Int): Single<List<DiaryEntity>>

    /* 查询日记的数据 */
    @Query("SELECT * FROM diaries WHERE id = :id")
    fun getData(id: Int): Single<List<DiaryEntity>>

    /* 删除某一篇日记 */
    @Query("DELETE FROM diaries WHERE id = :id")
    fun deleteData(id: Int)

    /* 清空数据表 */
    @Query("DELETE FROM diaries")
    fun clear()

}