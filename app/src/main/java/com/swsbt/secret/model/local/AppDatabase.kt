package com.swsbt.secret.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swsbt.secret.model.local.dao.DiaryDao
import com.swsbt.secret.model.local.entity.DiaryEntity

/* 每改变一次数据库，都要在version处加1 */
@Database(entities = [DiaryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        private val dbName = "app.db"       //数据库名

        @Volatile
        lateinit var INSTANCE: AppDatabase
        fun init(context: Context) {
            synchronized(this) {
                INSTANCE = buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .allowMainThreadQueries()
            .build()
    }

    abstract fun diaryDao(): DiaryDao

}