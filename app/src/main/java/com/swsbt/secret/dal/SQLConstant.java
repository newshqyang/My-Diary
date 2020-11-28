package com.swsbt.secret.dal;

public class SQLConstant {

    public static final int SQL_VERSION = 2;

    public static final String DATABASE_NAME = "my_diary";
    public static final String TABLE_NAME_ARTICLE = "article";

    // 日记表
    public static final String CREATE_TABLE_ARTICLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ARTICLE + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title VARCHAR(100)," +
            "content LONGTEXT," +
            "pictures TEXT," +
            "date VARCHAR(20))";

    /*
    SQL 方法
     */
    public static final String SEARCH_ALL = "SELECT * FROM article";

}
