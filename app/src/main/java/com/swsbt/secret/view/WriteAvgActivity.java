package com.swsbt.secret.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.DatabaseHelper;
import com.swsbt.secret.R;
import com.swsbt.secret.model.HomeButtonActivity;
import com.swsbt.secret.model.local.entity.AvgEditorBar;
import com.swsbt.secret.helper.utils.DateUtil;


import static com.swsbt.secret.helper.utils.CommonMethods.BackupSQL;
import static com.swsbt.secret.helper.utils.CommonMethods.CopyFile;

public class WriteAvgActivity extends HomeButtonActivity implements View.OnClickListener {

    private LinearLayout mLinearLayoutPictures;
    private TextView mSave;
    private AvgEditorBar mAvgEditorBar;

    //缓存用户输入内容
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long date;  // 写日记时的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_avg);
        initBaseComponent();
        initOthers();
    }


    //控件声明
    private void initBaseComponent(){
        mLinearLayoutPictures = findViewById(R.id.linearLayout_pictures);
        mSave =  findViewById(R.id.save);
        mAvgEditorBar = findViewById(R.id.avg_editor_bar);
        mAvgEditorBar.setActivity(this);
        //手动保存内容
        mSave.setOnClickListener(this);
    }

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    private void initOthers() {
        sharedPreferences = this.getSharedPreferences("PREES_CONF",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();              //显示缓存的内容
        //获取写作时间
        date = sharedPreferences.getLong("date", 0);
        date = date == 0 ? System.currentTimeMillis() : date;
        setTitle(DateUtil.convertYMDatetime(date));
    }

    //动态获取读写权限
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults){
        WriteAvgActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @Override
    public void onBackPressed(){
        navigator();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //获取图片路径
        String path = "";
        if (resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            /**
             * 复制文件至app内文件夹
             */
            String picture_path = CopyFile(this, uri,"" + date); //将路径转为标签
            mAvgEditorBar.addScene(picture_path);
        }
    }

    /*
    保存日记到数据库
     */
    private void saveToDatabase(){
        //重新编辑内容时，撤销因查找关键字导致的高亮
        String title;
        String content = "";
        title = getTitle(content);
        if (content.length() < 1){      //判断内容是否为空
            if (mAvgEditorBar.getClipSize() == 0) {
                navigator();
                return;
            } else {
                title = "***图片***";
            }
        }
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
//        values.put("pictures", JSONUtils.fromSet().toString());
//        values.put("city","上海-上海");
        values.put("date", date);
        //插入数据
        DatabaseHelper dbHelper = new DatabaseHelper(WriteAvgActivity.this, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(SQLConstant.TABLE_NAME_ARTICLE,null,values);
        db.close();
        values.clear();
        BackupSQL();
        navigator();
        date = 0;
        mLinearLayoutPictures.removeAllViews();
        saveCache();    // 清空缓存
    }

    /*
    跳转到日记列表
     */
    private void navigator() {
        startActivity(new Intent(this, OldMainActivity.class));
        overridePendingTransition(R.anim.normal, R.anim.left_exit);
        finishAfterTransition();
    }

    /*
    获取标题
     */
    private String getTitle(String content){
        //如果用户没有输入标题，判断输入内容，如果输入内容去除空格和回车后，小于10个字符，就将这几个字符作为标题
        //如果超过10个字符，就截取去除空格和回车后的前10个字符作为标题，同时后面加上省略号。
        int font_number_max = 10;
        String title = content.trim();           //获取content内容
        int end = title.indexOf("\n");          //获取掐头去尾后第一个换行符坐标
        if (end == -1){
            if (title.length() <= font_number_max){
                return title;
            }else {
                title = title.substring(0,font_number_max) + "...";
            }
        }else {
            if (title.substring(0,end).length() <= font_number_max){
                return title.substring(0,end);                                           //截取第一个换行符前的内容
            }else {
                title = title.substring(0,font_number_max) + "...";
            }
        }
        return title;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveToDatabase();
                break;
        }
    }

    /*
    执行缓存
     */
    private void saveCache() {
//        editor.putStringSet("pictureSet", mPictureSet); // 保存图片set
        editor.putString("content","");     //保存文字内容
        editor.putLong("date", date);
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigator();
        }
        return super.onOptionsItemSelected(item);
    }
}
