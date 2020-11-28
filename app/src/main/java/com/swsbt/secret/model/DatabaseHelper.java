package com.swsbt.secret.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.swsbt.secret.dal.SQLConstant;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建内容数据表
        String sql_table_create0 = SQLConstant.CREATE_TABLE_ARTICLE;
        db.execSQL(sql_table_create0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        //更新数据库操作，需要自增version
//        /**
//         * 天气有 cloudy、sunny、snowy、overcast、thunder、rainy
//         */
//        String sql = "ALTER TABLE article ADD COLUMN weather VARCHAR(8) DEFAULT \"🌤\";";
//        db.execSQL(sql);
//        /**
//         * 心情有 smile、happy、normal、sad、cry
//         */
//        sql = "ALTER TABLE article ADD COLUMN mood VARCHAR(6) DEFAULT \"😊\";";
//        db.execSQL(sql);
//        /**
//         * 背景随后添加，暂时是白色
//         */
//        sql = "ALTER TABLE article ADD COLUMN bg VARCHAR(30) DEFAULT \"white\";";
//        db.execSQL(sql);
//        /**
//         * 季节有 spring、summer、autumn、winter
//         */
//        sql = "ALTER TABLE article ADD COLUMN season CHAR(6) DEFAULT \"winter\";";
//        db.execSQL(sql);
//        /**
//         * 城市随后添加，暂时是上海
//         */
//        sql = "ALTER TABLE article ADD COLUMN city VARCHAR(30) DEFAULT \"上海-上海\";";
//        db.execSQL(sql);
    }
}
