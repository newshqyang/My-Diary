package com.swsbt.secret.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.dal.DiaryConfig;
import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.HomeButtonActivity;
import com.swsbt.secret.model.local.entity.Diary;
import com.swsbt.secret.model.DatabaseHelper;
import com.swsbt.secret.helper.adapter.DiaryAdapter;
import com.swsbt.secret.R;
import com.swsbt.secret.ui.widget.ConfirmDialog;
import com.swsbt.secret.ui.widget.ItemDiaryPopupMenu;
import com.swsbt.secret.util.AndroidConfigUtils;
import com.swsbt.secret.util.CommonMethods;
import com.swsbt.secret.util.UUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OldMainActivity extends HomeButtonActivity {

    private RecyclerView mRecyclerViewDiaryList;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;
    private List<Diary> mArticleList;
    DiaryAdapter myRVAdapter;

    int click_flag = 0;
    private final String SEARCH_ALL = SQLConstant.SEARCH_ALL;
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);

        }
        setContentView(R.layout.activity_main);

        UUtils.getReadWritePermissions(this);
        initComponent();
        others();
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /*
        加载组件
         */
    private void initComponent() {
        mRecyclerViewDiaryList = findViewById(R.id.switch_page);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerViewDiaryList.setLayoutManager(manager);
        // 日记列表
        myRVAdapter = new DiaryAdapter(this);
        myRVAdapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                Intent intent = new Intent(mContext, DiaryActivity.class);
                intent.putExtra("articleId", mArticleList.get(position).getId());
                startActivity(intent);
            }
        });
        myRVAdapter.setOnItemLongClickListener(new DiaryAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                ItemDiaryPopupMenu itemDiaryPopupMenu = new ItemDiaryPopupMenu(mContext, view);
                itemDiaryPopupMenu.setImpl(new ItemDiaryPopupMenu.ItemDiaryPopupMenuImpl() {
                    @Override
                    public void edit() {
                        new ConfirmDialog(mContext)
                                .setDoneText("编辑")
                                .setNoticeText(getString(R.string.item_diary_edit_confirm))
                                .setConfirmDialogImpl(new ConfirmDialog.ConfirmDialogImpl() {
                                    @Override
                                    public void done() {
                                        startActivity(new Intent(mContext, WriteActivity.class)
                                                .putExtra("mode", DiaryConfig.EDIT_MODE)
                                                .putExtra("id", mArticleList.get(position).getId()));
                                    }
                                }).show();
                    }

                    @Override
                    public void delete() {
                        new ConfirmDialog(mContext)
                                .setDoneText("移除")
                                .setNoticeText(getString(R.string.item_diary_delete_confirm))
                                .setConfirmDialogImpl(new ConfirmDialog.ConfirmDialogImpl() {
                                    @Override
                                    public void done() {
                                        CommonMethods.DELETE(mContext, "" + mArticleList.get(position).getId());
                                        load(SEARCH_ALL);
                                        mRecyclerViewDiaryList.scrollToPosition(position - 1);
                                        Toast.makeText(mContext, "已移除", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                    }
                });
                itemDiaryPopupMenu.show();
                return false;
            }
        });

//        myRVAdapter.setOnItemTouchListener(new DiaryAdapter.OnItemTouchListener() {
//            private int sx;
//            private int sy;
//            @Override
//            public boolean onItemTouch(View view, int position, MotionEvent event) {
//                // event.getRawX(); //获取手指第一次接触屏幕在x方向的坐标
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:// 获取手指第一次接触屏幕
//                        sx = (int) event.getRawX();
//                        sy = (int) event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动对应的事件
//                        int x = (int) event.getRawX();
//                        int y = (int) event.getRawY();
//                        // 获取手指移动的距离
//                        int dx = x - sx;
//                        // 得到imageView最开始的各顶点的坐标
//                        int l = view.getLeft();
//                        int r = view.getRight();
//                        int t = view.getTop();
//                        int b = view.getBottom();
//                        // 更改imageView在窗体的位置
//                        view.layout(l + dx, t, r + dx, b);
//                        // 获取移动后的位置
//                        sx = (int) event.getRawX();
//                        sy = (int) event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
//                        // 记录最后图片在窗体的位置
//                        int lasty = view.getTop();
//                        int lastx = view.getLeft();
//                        break;
//                }
//                return true;
//            }
//        });

        mRecyclerViewDiaryList.setAdapter(myRVAdapter);         //装载 myRVAdapter
        mRecyclerViewDiaryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {   // 当不滚动时记录位置
                    DiaryConfig.LIST_POSITION = manager.findFirstVisibleItemPosition();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /*
    加载其他对象
     */
    private void others() {
        mArticleList = new ArrayList<>();
        load(SEARCH_ALL);
    }

    /*
    加载列表
     */
    private void load(String sql){
        mArticleList.clear();
        mDBHelper = new DatabaseHelper(mContext, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
        mDB = mDBHelper.getReadableDatabase();
        Cursor cursor = mDB.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Diary article_item = new Diary(id,title,date);
            mArticleList.add(article_item);
        }
        cursor.close();
        mDB.close();                 //关闭、清理
        mDBHelper.close();
        Collections.reverse(mArticleList);         //按时间顺序，由后到前重新排序
        myRVAdapter.setArticleList(mArticleList);        //装填 日记属性集合
        mRecyclerViewDiaryList.setAdapter(myRVAdapter);
        mRecyclerViewDiaryList.scrollToPosition(DiaryConfig.LIST_POSITION);
    }

    @Override
    public void onResume(){
        load(SEARCH_ALL);
        super.onResume();
    }

    private final String KEY_NIGHT_THEME = "nightTheme";
    private boolean mNightTheme = false;
    private Menu mMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mMenu = menu;
        mNightTheme = AndroidConfigUtils.getBooleanValueDefaultFalse(this, KEY_NIGHT_THEME);
        if (mNightTheme) {
            menu.getItem(1).setIcon(getDrawable(R.drawable.icon_theme_day));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_write:
                writeDiary();
                break;
            case R.id.navigation_settings:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.navigation_theme:
                switchTheme();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 切换主题
     */
    private void switchTheme() {
        mNightTheme = !mNightTheme;
        if (mNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mMenu.getItem(1).setIcon(getDrawable(R.drawable.icon_theme_day));
        } else {
            mMenu.getItem(1).setIcon(getDrawable(R.drawable.icon_theme_night));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        AndroidConfigUtils.saveBoolean(this, KEY_NIGHT_THEME, mNightTheme);
    }

    /*
    写日记
     */
    private void writeDiary() {
        startActivity(new Intent(mContext, WriteActivity.class));
//        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.navigation_write));
//        popupMenu.getMenuInflater().inflate(R.menu.write_menu, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.normal_diary) {
//                    startActivity(new Intent(mContext, WriteActivity.class));
//                } else if (item.getItemId() == R.id.avg_diary) {
//                    startActivity(new Intent(mContext, WriteAvgActivity.class));
//                }
//                return false;
//            }
//        });
//        popupMenu.show();
    }

    /*
                搜索功能
                 */
    private void search() {
        click_flag++;
//        if (click_flag == 1){
//            mSearch.setVisibility(View.VISIBLE);
//            mSearchButton.setImageDrawable(getResources().getDrawable(R.drawable.search,null));
//        }else {
//            mSearch.setVisibility(View.INVISIBLE);
//            mSearchButton.setImageDrawable(getResources().getDrawable(R.drawable.switch_page_search_off,null));
//            mSearch.setText("");
//            click_flag = 0;
//        }
    }
}
