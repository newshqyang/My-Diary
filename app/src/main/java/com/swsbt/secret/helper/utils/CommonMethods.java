package com.swsbt.secret.helper.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethods {

    public static String GetMaxId(Activity activity){
        //获取当前article表中最大id
        String maxId = "-1";
        DatabaseHelper dbHelper = new DatabaseHelper(activity,SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM article ORDER BY ID DESC LIMIT 1",null);
        while (cursor.moveToNext()){
            maxId = cursor.getString(cursor.getColumnIndex("id"));
        }
        return maxId;
    }

    public static String GetSeason(){
        //根据月份转换季节
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String season = "";
        switch (month){
            case 12:
            case 1:
            case 2:
                season = "winter";
                break;
            case 3:
            case 4:
            case 5:
                season = "spring";
                break;
            case 6:
            case 7:
            case 8:
                season = "summer";
                break;
            case 9:
            case 10:
            case 11:
                season = "autumn";
                break;
        }
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String week_string = "";
        switch (week){
            case 1:

        }
        return season;
    }

    public static String MonthTranslateChinese(int month_number){
        //数字月份转中文
        String translate_result = "";
        switch (month_number){
            case 1:
                translate_result = "一月";
                break;
            case 2:
                translate_result = "二月";
                break;
            case 3:
                translate_result = "三月";
                break;
            case 4:
                translate_result = "四月";
                break;
            case 5:
                translate_result = "五月";
                break;
            case 6:
                translate_result = "六月";
                break;
            case 7:
                translate_result = "七月";
                break;
            case 8:
                translate_result = "八月";
                break;
            case 9:
                translate_result = "九月";
                break;
            case 10:
                translate_result = "十月";
                break;
            case 11:
                translate_result = "十一月";
                break;
            case 12:
                translate_result = "十二月";
                break;
        }
        return translate_result;
    }

    public static String MonthTranslate(int month_number){
        //数字月份转英文缩写
        String translate_result = "";
        switch (month_number){
            case 1:
                translate_result = "Jan";
                break;
            case 2:
                translate_result = "Feb";
                break;
            case 3:
                translate_result = "Mar";
                break;
            case 4:
                translate_result = "Apr";
                break;
            case 5:
                translate_result = "May";
                break;
            case 6:
                translate_result = "Jun";
                break;
            case 7:
                translate_result = "Jul";
                break;
            case 8:
                translate_result = "Aug";
                break;
            case 9:
                translate_result = "Sep";
                break;
            case 10:
                translate_result = "Oct";
                break;
            case 11:
                translate_result = "Nov";
                break;
            case 12:
                translate_result = "Dec";
                break;
        }
        return translate_result;
    }

    //备份时需要用到的权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //权限设置状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    public static void getWriteReadFilePermissions(Activity activity){
        if (ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_PERMISSION_CODE);
        }
    }

    public static SpannableString Translating (Activity activity,String input){
        //将图片标签转为bitmap
        WindowManager windowManager = activity.getWindowManager();  //获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] windowSize = {displayMetrics.widthPixels,displayMetrics.heightPixels};

        //将图片那一串字符串解析出来,即<img src=="xxx" />
        Pattern pattern = Pattern.compile("\\⇞⇝.*?\\⇜⇞");
        Matcher matcher = pattern.matcher(input);

        //使用SpannableString
        SpannableString spannableString = new SpannableString(input);
        while (matcher.find()){
            String s = matcher.group();     //这里s保存的是整个标签，即<img src=="xxx" />，start和end保存的是下标
            int start = matcher.start();
            int end = matcher.end();

            String backupFilePath = s.replaceAll("\\⇞⇝|\\⇜⇞","").trim();   //path是去掉☺的中间的图片路径
            File file = AndroidFileUtils.getFileFromUri(activity, Uri.fromFile(new File(backupFilePath)));     //如果这张图片不存在，就continue
            if (!file.exists()){
                continue;
            }
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap == null) {
                Toast.makeText(activity, "获取图片失败", Toast.LENGTH_SHORT).show();
            }
            if (bitmap.getWidth() >= windowSize[0] * 4/5){
                //如果图片超过屏幕大小，缩小图片
                int[] bmpSize = {bitmap.getWidth(),bitmap.getHeight()};
                Matrix matrix = new Matrix();
                //等比例缩放
                float size = (float)windowSize[0] *4/5 / bmpSize[0];
                matrix.postScale(size,size);
                bitmap = Bitmap.createBitmap(bitmap,0,0,bmpSize[0],bmpSize[1],matrix,true);
            }

            ImageSpan imageSpan = new ImageSpan(activity,bitmap);
            spannableString.setSpan(imageSpan,start,end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static int RecoverSQL(Context context){
        //恢复日记sql文件
        String sqlPath = "/data/data/com.swsbt.secret/databases/my_diary";
        String backupPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary/my_diary_backup";
        File file = AndroidFileUtils.getFileFromUri(context, Uri.fromFile(new File(backupPath)));
        if (!file.exists()){
            return 0;
        }
        try {
            int byteSum = 0;
            int byteRead = 0;
            File sqlFile = AndroidFileUtils.getFileFromUri(context, Uri.fromFile(new File(sqlPath)));

            if (!sqlFile.exists()){
                sqlFile.createNewFile();
            }
            InputStream inputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(sqlFile);
            byte[] bytes = new byte[1444];
            int length;
            while ((byteRead = inputStream.read(bytes)) != -1){
                byteSum += byteRead;
                fileOutputStream.write(bytes,0,byteRead);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void BackupSQL(){
        //备份日记sql文件
        File file = new File("/data/data/com.swsbt.secret/autoBackupOn");
        if (!file.exists()){
            return;
        }
        String sqlPath = "/data/data/com.swsbt.secret/databases/my_diary";
        String backupPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary/my_diary_backup";
        String backupFolderPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary";

        try {
            int byteSum = 0;
            int byteRead = 0;
            File ImgFile = new File(sqlPath);
            File backupFile = new File(backupPath);
            //如果文件夹不存在就创建
            File backupFolder = new File(backupFolderPath);
            if (!backupFolder.exists()){
                backupFolder.mkdirs();
            }
            if (!backupFile.exists()){
                backupFile.createNewFile();
            }
            if (ImgFile.exists()){
                InputStream inputStream = new FileInputStream(ImgFile);
                FileOutputStream fileOutputStream = new FileOutputStream(backupFile);
                byte[] bytes = new byte[1444];
                int length;
                while ((byteRead = inputStream.read(bytes)) != -1){
                    byteSum += byteRead;
                    fileOutputStream.write(bytes,0,byteRead);
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    复制文件，返回文件的uri的path
     */
    public static String CopyFile(Context context, Uri imgUri,String dateline){
        String fileName = String.valueOf(System.currentTimeMillis());
        //备份插入的图片
        String backupPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary/my_diary_img_backup/"+dateline+"/"+fileName;
        String backupFolderPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary/my_diary_img_backup/"+dateline;

        try {
            int byteSum = 0;
            int byteRead = 0;
            File ImgFile = AndroidFileUtils.getFileFromUri(context, imgUri);
            File backupFile = new File(backupPath);
            //如果文件夹不存在就创建
            File backupFolder = new File(backupFolderPath);
            if (!backupFolder.exists()){
                backupFolder.mkdirs();
            }
            if (!backupFile.exists()){
                backupFile.createNewFile();
            }
            if (ImgFile.exists()){
                InputStream inputStream = new FileInputStream(ImgFile);
                FileOutputStream fileOutputStream = new FileOutputStream(backupFile);
                byte[] bytes = new byte[1444];
                int length;
                while ((byteRead = inputStream.read(bytes)) != -1){
                    byteSum += byteRead;
                    fileOutputStream.write(bytes,0,byteRead);
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backupPath;
    }
    public static void DeleteFiles(String dateline){
        //删除日记对应图片文件夹
        String folderPath = Environment.getExternalStorageDirectory() + "/backup/LockDiary/my_diary_img_backup/"+dateline;
        deleteDir(folderPath);
    }
    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        //https://www.cnblogs.com/zhangfeitao/p/6733872.html
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }
    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static void DELETE(Context context,String id){
        //数据库删除条目
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQLConstant.DATABASE_NAME, null, SQLConstant.SQL_VERSION);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SQLConstant.TABLE_NAME_ARTICLE,"id=?",new String[]{id});
        db.close();
        dbHelper.close();
    }

    public static String QUERY_DATA(Context context,String dbName,String tableName,String selection,String selectionArgs,String columnName){
        //查询单个数据
        DatabaseHelper dbHelper = new DatabaseHelper(context,dbName,null, SQLConstant.SQL_VERSION);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        String data = "";
        //查询
        Cursor cursor = db.query(tableName,new String[]{columnName},selection,new String[]{selectionArgs},
                null,null,null);
        while (cursor.moveToNext()){
            data = cursor.getString(cursor.getColumnIndex(columnName));
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return data;
    }

    public static void DO_LOG( Activity activity,int flag,String id){
        //记录用户操作于数据库中
        String do_name = "";
        if (flag == 1){
            do_name = "成功进入APP";
        }else if (flag == 2){
            do_name = "写入新的日记";
        }else if (flag == 3){
            do_name = "重新编辑《"+QUERY_DATA(activity,"article","article","id=?",
                    id,"title")+"》";
        }else if (flag == 4){
            do_name = "查看《"+QUERY_DATA(activity,"article","article","id=?",
                    id,"title")+"》";
        }else if (flag == 5){
            do_name = "删除《"+QUERY_DATA(activity,"article","article","id=?",
                    id,"title")+"》";
        }else if (flag == 6){
            do_name = "修改口令失败";
        }else if (flag == 7){
            do_name = "成功修改口令";
        }
        DatabaseHelper dbHelper = new DatabaseHelper(activity,SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date",System.currentTimeMillis());
        values.put("do",do_name);
        db.insert("secret_log",null,values);
        values.clear();
        db.close();
        dbHelper.close();
    }
    public static String DateTime_Year_Month_Day(){
        Calendar calendar = Calendar.getInstance();     //获取系统时间
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date_year_month_day = year + "年" + month + "月" + day +"日";
        return date_year_month_day;
    }
    public static String DateTime_Hour_Minute_second(){
        Calendar calendar = Calendar.getInstance();     //获取系统时间
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String minutes = "" + minute;
        int second = calendar.get(Calendar.SECOND);
        String seconds = "" + second;
        if (minute < 10){
            minutes = "0" + minute;
        }
        if (second < 10){
            seconds = "0" + second;
        }
        String date_hour_minute_second = hour +":"+ minutes + ":" + seconds;
        return date_hour_minute_second;
    }


    //搜索功能
    //搜索关键字方法并高亮
    public static void SearchKeyCode(String keyWords,TextView textView){
        if (textView != null){
            SpannableString spannableString = new SpannableString(textView.getText());
            Pattern pattern = Pattern.compile(keyWords);
            Matcher matcher = pattern.matcher(spannableString);
            while (matcher.find()){
                int start = matcher.start();
                int end = matcher.end();
                spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#00ffff")),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setText(spannableString);
        }
    }

    //编辑页面搜索关键字方法并高亮
    public static void SearchKeyCodeWritePage(String keyWords,EditText editText){
        if (editText != null){
            SpannableString spannableString = new SpannableString(editText.getText());
            Pattern pattern = Pattern.compile(keyWords);
            Matcher matcher = pattern.matcher(spannableString);
            while (matcher.find()){
                int start = matcher.start();
                int end = matcher.end();
                spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#00ffff")),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            editText.setText(spannableString);
        }
    }

    public static void WRITE_HIGHLIGHT_CANCEL(EditText contentEdit){
        //重新编辑内容时，撤销因查找关键字导致的高亮
        SpannableString spannableString = new SpannableString(contentEdit.getText());
        Pattern pattern = Pattern.compile(contentEdit.getText().toString());
        HIGHLIGHT_CANCEL(spannableString,pattern);
        contentEdit.setText(spannableString);
    }
    public static void LOOK_HIGHLIGHT_CANCEL(TextView content_board){
        //查看页面中撤销高亮
        SpannableString spannableString = new SpannableString(content_board.getText());
        Pattern pattern = Pattern.compile(content_board.getText().toString());
        HIGHLIGHT_CANCEL(spannableString,pattern);
        content_board.setText(spannableString);
    }
    private static void HIGHLIGHT_CANCEL(SpannableString spannableString, Pattern pattern){
        //取消文字高亮
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#ffffff")),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#656565")),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
