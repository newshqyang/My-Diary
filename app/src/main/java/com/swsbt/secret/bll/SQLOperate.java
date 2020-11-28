package com.swsbt.secret.bll;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.DatabaseHelper;
import com.swsbt.secret.model.entity.Diary;

public class SQLOperate {

    /*
    查询一组数据
     */
    public static Diary getArticleInfo(Context context, int id) {
        //查询单个数据
        DatabaseHelper dbHelper = new DatabaseHelper(context,SQLConstant.DATABASE_NAME,null, SQLConstant.SQL_VERSION);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        Diary articleItem = new Diary();
        //查询
        Cursor cursor = db.query(SQLConstant.TABLE_NAME_ARTICLE, null, "id=?",new String[]{"" + id},
                null,null,null);
        while (cursor.moveToNext()){
            articleItem.setId(id);
            articleItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            articleItem.setDate(cursor.getString(cursor.getColumnIndex("date")));
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return articleItem;
    }

}
