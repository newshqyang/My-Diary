package com.swsbt.secret.helper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.R;
import com.swsbt.secret.model.local.entity.Diary;
import com.swsbt.secret.model.local.entity.DiaryViewHolder;

import java.util.Calendar;
import java.util.List;

import static com.swsbt.secret.helper.utils.DateUtil.MonthTranslate;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Diary> mArticleList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        //添加点击接口
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        //点击操作
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemLongClickListener mOnItemLongClickListener;
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    private OnItemTouchListener mOnItemTouchListener;
    public interface OnItemTouchListener {
        boolean onItemTouch(View view, int position, MotionEvent event);
    }
    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        mOnItemTouchListener = onItemTouchListener;
    }

    public void setArticleList(List<Diary> articleList) {
        //获取日记属性集合
        mArticleList = articleList;
    }

    public DiaryAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DiaryViewHolder(mLayoutInflater.inflate(R.layout.main_diary_item,viewGroup,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final DiaryViewHolder viewHolder, final int i) {
        Diary item = mArticleList.get(i);
        viewHolder.articleTitle.setText(item.getTitle());         //标题

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(item.getDate()));
        String yearMonthNow = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月";

        if (i == 0) {
            viewHolder.textViewDateline.setText("-  " + yearMonthNow + "  -");
            viewHolder.textViewDateline.setVisibility(View.VISIBLE);
        } else {
            Calendar calendarPre = Calendar.getInstance();
            calendarPre.setTimeInMillis(Long.parseLong(mArticleList.get(i - 1).getDate()));
            String yearMonthPre = calendarPre.get(Calendar.YEAR) + "年" + (calendarPre.get(Calendar.MONTH) + 1) + "月";
            if (!yearMonthPre.equals(yearMonthNow)) {    // 显示时间线
                viewHolder.textViewDateline.setText("-  " + yearMonthNow + "  -");
                viewHolder.textViewDateline.setVisibility(View.VISIBLE);
            } else {
                viewHolder.textViewDateline.setVisibility(View.GONE);
            }
        }
        Calendar calendarNowDate = Calendar.getInstance();
        String yearMonth = calendarNowDate.get(Calendar.YEAR) + "年" + (calendarNowDate.get(Calendar.MONTH) + 1) + "月";
        if (yearMonthNow.equals(yearMonth)) {   // 最近的就隐藏
            viewHolder.textViewDateline.setVisibility(View.GONE);
        }

        viewHolder.textViewMonth.setText(MonthTranslate(calendar.get(Calendar.MONTH) + 1));       //提取日期中月份
        viewHolder.textViewDay.setText("" + calendar.get(Calendar.DAY_OF_MONTH)); //提取日期中的日
        viewHolder.textViewTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));          //获取日记具体时间
        if (mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView,position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    boolean ret = mOnItemLongClickListener.onItemLongClick(viewHolder.itemView, position);
                    return ret;
                }
            });
        }
        if (mOnItemTouchListener != null) {
            viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int position = viewHolder.getLayoutPosition();
                    boolean ret = mOnItemTouchListener.onItemTouch(viewHolder.itemView, position, event);
                    return ret;
                }
            });
        }
//        viewHolder.itemView.startAnimation(AnimationUtils.makeInAnimation(mContext, true));
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
