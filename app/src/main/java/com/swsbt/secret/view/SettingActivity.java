package com.swsbt.secret.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.DatabaseHelper;
import com.swsbt.secret.R;
import com.swsbt.secret.model.HomeButtonActivity;
import com.swsbt.secret.util.CommonMethods;

import java.io.File;
import java.io.IOException;

import static com.swsbt.secret.util.CommonMethods.RecoverSQL;

public class SettingActivity extends HomeButtonActivity {

    private LinearLayout mClear;
    private LinearLayout mRecover;
    private Switch mAutoBackup;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置");
        setContentView(R.layout.activity_setting);
        initComponent();
        BUTTON();
    }

    //动态获取读写权限
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults){
        SettingActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    //弹出恢复日记对话框
    private void showRecoverDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.clear_warning);
        builder.setTitle("注意");
        builder.setMessage("恢复操作会覆盖掉当前所有日记内容！您确定要恢复吗？\n" +
                "如确定，请先在外部存储的backup文件夹中创建LockDiary文件夹，并将my_diary_backup文件放至该处");
        //左边取消按钮
        builder.setNegativeButton("不，算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //右边确认删除按钮
        builder.setPositiveButton("恢复", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (RecoverSQL(SettingActivity.this) == 1){
                    Toast.makeText(SettingActivity.this,"日记内容已恢复",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SettingActivity.this,"未发现日记备份文件",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //弹出删除所有数据的选择对话框
    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.clear_warning);
        builder.setTitle("WARNING");
        builder.setMessage("此举将删除APP内所有数据及日志文件！确定要这么做吗？");
        //左边取消按钮
        builder.setNegativeButton("不，算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //右边确认删除按钮
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper dbHelper = new DatabaseHelper(SettingActivity.this, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sql = "delete from article";
                db.execSQL(sql);
                db.close();

                dbHelper = new DatabaseHelper(SettingActivity.this,SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
                db = dbHelper.getWritableDatabase();
                sql = "delete from secret_log";
                db.execSQL(sql);
                db.close();
                Toast.makeText(SettingActivity.this,"所有数据已删除",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void BUTTON() {
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        //判断APP包目录下是否存在自动备份flag文件
        String autoBackupFlagPath = "/data/data/com.swsbt.secret/autoBackupOn";
        final File file1 = new File(autoBackupFlagPath);
        if (file1.exists()){
            mAutoBackup.setChecked(true);   //如果存在，将switch设置为默认开启
        }else {
            mAutoBackup.setChecked(false);  //如果不存在，将switch设置为默认关闭
        }
        mAutoBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){     //如果用户切换switch
                    try {
                        //获取权限
                        CommonMethods.getWriteReadFilePermissions(SettingActivity.this);
                        file1.createNewFile();       //创建flag文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    file1.delete();          //如果用户关闭自动备份，删除flag文件
                }
            }
        });

        //通过备份恢复日记内容
        mRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverDialog();
            }
        });
        LinearLayout notice = findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }

    //声明控件
    private void initComponent(){
        mClear = findViewById(R.id.clear);
        mAutoBackup = findViewById(R.id.backup);
        mRecover = findViewById(R.id.recover);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, OldMainActivity.class));
        overridePendingTransition(R.anim.left_enter, R.anim.right_exit);
    }
}
