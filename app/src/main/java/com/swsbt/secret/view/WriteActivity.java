package com.swsbt.secret.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.swsbt.secret.bll.SQLOperate;
import com.swsbt.secret.dal.DiaryConfig;
import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.DatabaseHelper;
import com.swsbt.secret.R;
import com.swsbt.secret.model.HomeButtonActivity;
import com.swsbt.secret.model.local.entity.Diary;
import com.swsbt.secret.helper.utils.AndroidConfigUtils;
import com.swsbt.secret.helper.utils.AndroidFileUtils;
import com.swsbt.secret.helper.utils.CommonMethods;
import com.swsbt.secret.helper.utils.DateUtils;
import com.swsbt.secret.helper.utils.ImageUtils;
import com.swsbt.secret.helper.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static com.swsbt.secret.helper.utils.CommonMethods.BackupSQL;
import static com.swsbt.secret.helper.utils.CommonMethods.CopyFile;
import static com.swsbt.secret.helper.utils.CommonMethods.SearchKeyCodeWritePage;
import static com.swsbt.secret.helper.utils.CommonMethods.WRITE_HIGHLIGHT_CANCEL;

public class WriteActivity extends HomeButtonActivity implements View.OnClickListener {

    private EditText mEditTextContent;
    private LinearLayout mLinearLayoutPictures;
    private TextView mSave;
    private EditText search;
    private ImageView mSearchButton;
    private ImageView mTab;
    private ImageView mSearchClear;
    private ImageView mPictures;


    private boolean mSearchOffset = false;  // 搜索开关
    private boolean mTabOffset = false;     // 缩进开关

    //缓存用户输入内容
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String temp;
    private Set<String> mPictureSet;
    private long date;  // 写日记时的时间

    private int mMode = 0;   // 0是写模式，1是修改模式
    private int mId = -1;   // 编辑模式，日记的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean theme = AndroidConfigUtils.getBooleanValueDefaultFalse(this, "nightTheme");
        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        setContentView(R.layout.activity_write);
        mMode = getIntent().getIntExtra("mode", DiaryConfig.WRITE_MODE);
        initBaseComponent();
        initOthers();
    }


    //控件声明
    private void initBaseComponent(){
        mEditTextContent = findViewById(R.id.content_edit);
        mLinearLayoutPictures = findViewById(R.id.linearLayout_pictures);
        search = findViewById(R.id.search);
        mSearchButton =  findViewById(R.id.search_button);
        mSave =  findViewById(R.id.save);
        mTab = findViewById(R.id.tab);
        mSearchClear = findViewById(R.id.search_button_clear);
        mPictures = findViewById(R.id.pictures);
        mPictures.setOnClickListener(this);
        mTab.setOnClickListener(this);
        mEditTextContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //换行时开头空两格
                if (mTabOffset){
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        int index = mEditTextContent.getSelectionStart();
                        Editable editable = mEditTextContent.getText();
                        editable.insert(index,"        ");
                    }
                }
                return false;
            }
        });
        mEditTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mEditTextContent.getText().toString();
                if (text.length() == 0) {
                    mSave.setEnabled(false);
                    mSave.setBackground(getDrawable(R.drawable.button_all_right_background_disabled));
                    return;
                }
                mSave.setEnabled(true);
                mSave.setBackground(getDrawable(R.drawable.button_all_right_background));
                if (mMode == DiaryConfig.WRITE_MODE) {
                    editor.putString("content",mEditTextContent.getText().toString());     //保存文字内容
                    editor.putLong("date", date);
                    editor.apply();//监听键盘操作，用户每次输入都会缓存数据
                }
            }
        });
        mSearchClear.setOnClickListener(this);
        //搜索区
        search.addTextChangedListener(new TextWatcher() {
            //实时监听搜索框
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                WRITE_HIGHLIGHT_CANCEL(mEditTextContent);
                SearchKeyCodeWritePage(String.valueOf(search.getText()),mEditTextContent);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mSearchClear.setVisibility(View.VISIBLE);
                }else {
                    mSearchClear.setVisibility(View.GONE);
                }
            }
        });
        //点击搜索图标，显示搜索框
        mSearchButton.setOnClickListener(this);
        //手动保存内容
        mSave.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initOthers() {
        if (mMode == DiaryConfig.WRITE_MODE) {
            writeModeInitCache();
            return;
        }
        editModeInitDiary();
    }

    /**
     * 编辑模式，加载日记内容
     */
    private void editModeInitDiary() {
        mId = getIntent().getIntExtra("id", -1);
        if (mId == -1) {
            return;
        }
        Diary diary = SQLOperate.getArticleInfo(this, mId);
        date = Long.parseLong(diary.getDate());
        setTitle(DateUtils.getYMDHM(date));
        mEditTextContent.setText(CommonMethods.QUERY_DATA(this,
                SQLConstant.DATABASE_NAME,SQLConstant.TABLE_NAME_ARTICLE,
                "id=?", "" + mId,"content"));
        mEditTextContent.setSelection(mEditTextContent.getText().toString().length());
        String pictures = CommonMethods.QUERY_DATA(getApplicationContext(),
                SQLConstant.DATABASE_NAME,SQLConstant.TABLE_NAME_ARTICLE,
                "id=?", "" + mId,"pictures");
        try {
            mPictureSet = (Set<String>) JSONUtils.toSet(new JSONArray(pictures));
            for (String path : mPictureSet) {
                mLinearLayoutPictures.addView(createPictureLayout(path));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写作模式，加载缓存
     */
    private void writeModeInitCache() {
        sharedPreferences = this.getSharedPreferences("PREES_CONF",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();              //显示缓存的内容
        mEditTextContent.setText(sharedPreferences.getString("content",""));
        mEditTextContent.setSelection(mEditTextContent.getText().toString().length());
        //获取写作时间
        date = sharedPreferences.getLong("date", 0);
        date = date == 0 ? System.currentTimeMillis() : date;
        setTitle(DateUtils.convertYMDatetime(date));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPictureSet = new HashSet<>(sharedPreferences.getStringSet("pictureSet", new HashSet<String>()));
                        for (String path : mPictureSet) {
                            mLinearLayoutPictures.addView(createPictureLayout(path));
                        }
                    }
                });
            }
        }).start();
    }

    //动态获取读写权限
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults){
        WriteActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    @Override
    public void onBackPressed(){
        if (mSearchOffset){
            //如果没有关闭搜索框就关闭
            mSearchButton.setImageDrawable(getResources().getDrawable(R.drawable.search_off,getTheme()));
            search.setText("");
            search.setVisibility(View.INVISIBLE);
            WRITE_HIGHLIGHT_CANCEL(mEditTextContent);
            mSearchOffset = false;
            mEditTextContent.setSelection(mEditTextContent.getText().toString().length());
            return;
        }
        navigator();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取图片路径
        String path = "";
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            /*
              复制文件至app内文件夹
             */
            String pictures_path = CopyFile(this, uri, "" + date); //将路径转为标签
            mPictureSet.add(pictures_path);
            mLinearLayoutPictures.addView(createPictureLayout(pictures_path));
            if (mMode == DiaryConfig.WRITE_MODE) {
                editor.putStringSet("pictureSet", mPictureSet); // 保存图片set
                editor.putLong("date", date);
                editor.apply();//监听图片操作，用户每次插入图片都会缓存图片数据
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    创建小图片布局
     */
    private RelativeLayout createPictureLayout(final String picturePath) {
        final RelativeLayout pictureLayout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.layout_picture, null);
        ImageView picture = pictureLayout.findViewById(R.id.imageView_picture);
        Bitmap bitmap = BitmapFactory.decodeFile(AndroidFileUtils.getFileFromUri(this, Uri.fromFile(new File(picturePath))).getAbsolutePath());
        picture.setImageBitmap(ImageUtils.cutBitmap(bitmap));
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WriteActivity.this, ImageActivity.class)
                        .putExtra(ImageActivity.IMAGE_PATH, picturePath));
            }
        });
        ImageButton cancel = pictureLayout.findViewById(R.id.imageButton_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutPictures.removeView(pictureLayout);
                mPictureSet.remove(picturePath);
                if (mMode == DiaryConfig.WRITE_MODE) {
                    editor.putStringSet("pictureSet", mPictureSet); // 保存图片set
                    editor.putLong("date", date);
                    editor.apply();//监听图片操作，用户每次去掉图片都会缓存图片数据
                }
            }
        });
        return pictureLayout;
    }

    /*
    保存日记到数据库
     */
    private void saveToDatabase(){
        if (mMode == DiaryConfig.WRITE_MODE) {
            writeModeSave2DB();
        } else if (mMode == DiaryConfig.EDIT_MODE) {
            editModeUpdate2DB();
        }
    }

    /**
     * 编辑模式，更新数据库
     */
    private void editModeUpdate2DB() {
        //重新编辑内容时，撤销因查找关键字导致的高亮
        String title;
        String content = mEditTextContent.getText().toString();
        title = getTitle(content);
        if (content.length() < 1){      //判断内容是否为空
            if (mPictureSet.size() == 0) {
                navigator();
                return;
            } else {
                title = "***图片***";
            }
        }
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("pictures", JSONUtils.fromSet(mPictureSet).toString());
//        values.put("city","上海-上海");
        values.put("date", date);
        //插入数据
        DatabaseHelper dbHelper = new DatabaseHelper(WriteActivity.this, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(SQLConstant.TABLE_NAME_ARTICLE, values, "id=?",new String[]{"" + mId});
        db.close();
        values.clear();
        BackupSQL();
        navigator();
    }

    /**
     * 写作模式保存数据库
     */
    private void writeModeSave2DB() {
        //重新编辑内容时，撤销因查找关键字导致的高亮
        String title;
        String content = mEditTextContent.getText().toString();
        title = getTitle(content);
        if (content.length() < 1){      //判断内容是否为空
            if (mPictureSet.size() == 0) {
                navigator();
                return;
            } else {
                title = "***图片***";
            }
        }
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("pictures", JSONUtils.fromSet(mPictureSet).toString());
//        values.put("city","上海-上海");
        values.put("date", date);
        //插入数据
        DatabaseHelper dbHelper = new DatabaseHelper(WriteActivity.this, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(SQLConstant.TABLE_NAME_ARTICLE,null,values);
        db.close();
        values.clear();
        BackupSQL();
        navigator();
        date = 0;
        mEditTextContent.setText("");
        mPictureSet = new HashSet<>();
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
            case R.id.pictures:
                addPictures();
                break;
            case R.id.tab:
                tabControl();
                break;
            case R.id.save:
                saveToDatabase();
                break;
            case R.id.search_button_clear:
                clearSearchEditText();
                break;
            case R.id.search_button:
                searchControl();
                break;
        }
    }

    /*
    执行缓存
     */
    private void saveCache() {
        editor.putStringSet("pictureSet", mPictureSet); // 保存图片set
        editor.putString("content",mEditTextContent.getText().toString());     //保存文字内容
        editor.putLong("date", date);
        editor.apply();
    }

    /*
    缩进开关控制
     */
    private void tabControl() {
        if (!mTabOffset){   // 开启缩进开关
            mTabOffset = true;
            mTab.setImageDrawable(getResources().getDrawable(R.drawable.tab,getTheme()));
        }else {
            mTabOffset = false;
            mTab.setImageDrawable(getResources().getDrawable(R.drawable.tab_off,getTheme()));
        }
    }

    /*
    添加图片
     */
    private void addPictures() {
        //获取读写权限
        CommonMethods.getWriteReadFilePermissions(WriteActivity.this);
        //打开图片管理器
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    /*
    搜索控制
     */
    private void searchControl() {
        if (!mSearchOffset){
            mSearchOffset = true;
            mSearchButton.setImageDrawable(getResources().getDrawable(R.drawable.search,getTheme()));
            search.setVisibility(View.VISIBLE);
        }else {
            mSearchOffset = false;
            //点击两次搜索图标，撤销高亮
            mSearchButton.setImageDrawable(getResources().getDrawable(R.drawable.search_off,getTheme()));
            search.setText("");
            search.setVisibility(View.INVISIBLE);
            WRITE_HIGHLIGHT_CANCEL(mEditTextContent);
            mEditTextContent.setSelection(mEditTextContent.getText().toString().length());
        }
    }

    /*
    清空搜索框
     */
    private void clearSearchEditText() {
        search.setText("");
        WRITE_HIGHLIGHT_CANCEL(mEditTextContent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigator();
        }
        return super.onOptionsItemSelected(item);
    }
}
