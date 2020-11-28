//package com.swsbt.secret.view;
//
//import android.app.DatePickerDialog;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.DatePicker;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.swsbt.secret.dal.SQLConstant;
//import com.swsbt.secret.model.BaseActivity;
//import com.swsbt.secret.model.DatabaseHelper;
//import com.swsbt.secret.model.entity.MyLog;
//import com.swsbt.secret.R;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.List;
//
//public class LogListViewActivity extends BaseActivity {
//
//    private ListView lv;
//    private ImageView mClassified_display;
//
//    private List<MyLog> logList;
//    private DatabaseHelper dbHelper;
//    private SQLiteDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_list_view);
//        DECLARATION_CONTROL();
//        logList = new ArrayList<MyLog>();
//        LOAD(null,null);
//        BUTTON_CLICK();
//    }
//
//    class MyAdapter extends BaseAdapter {
//        //自定义适配器
//        @Override
//        public int getCount() {
//            return logList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            TextView log_view = new TextView(LogListViewActivity.this);
//            log_view.setTextSize(25);
//            MyLog log = logList.get(position);
//            String data = log.get_dateline() + "\n|\t\t\t\t" + log.get_do_thing();
//            log_view.setText(data);
//            return log_view;
//        }
//    }
//
//    //加载日志内容
//    private void LOAD(String selection,String[] selectionArgs){
//        logList.clear();
//        dbHelper = new DatabaseHelper(LogListViewActivity.this, SQLConstant.DATABASE_NAME,null,SQLConstant.SQL_VERSION);
//        db = dbHelper.getWritableDatabase();
//        //查询
//        Cursor cursor = db.query("log", null,
//                selection,selectionArgs,null,null,null);
//        while (cursor.moveToNext()){
//            String id = cursor.getString(cursor.getColumnIndex("id"));
//            String date = cursor.getString(cursor.getColumnIndex("date"));
//            String doThing = cursor.getString(cursor.getColumnIndex("do_thing"));
//            MyLog log = new MyLog(id, date, doThing);
//            logList.add(log);
//        }
//        db.close();
//        Collections.reverse(logList);
//        lv.setAdapter(new MyAdapter());
//    }
//    //按钮操作
//    private void BUTTON_CLICK(){
//        mClassified_display.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击分类图标，弹出日历，用户选择日期后显示对应日期的操作记录
//                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String dateline = year + "年" + (month+1) + "月" + dayOfMonth + "日";
//                        LOAD("year_month_day=?",new String[]{dateline});
//                    }
//                };
//                Calendar calendar = Calendar.getInstance();     //获取系统时间
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(LogListViewActivity.this,dateSetListener,year,month,day);
//                datePickerDialog.show();
//            }
//        });
//    }
//
//    //声明控件
//    private void DECLARATION_CONTROL(){
//        lv = (ListView)findViewById(R.id.list_view);
//        mClassified_display = (ImageView)findViewById(R.id.classified_display);
//    }
//
//    @Override
//    protected void onResume(){
//        //重新加载这个活动页面时，刷新数据
//        super.onResume();
//        LOAD(null,null);
//    }
//}
