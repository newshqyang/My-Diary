package com.swsbt.secret.model.local.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * DragScaleView
 * Created by hanswim on 15-1-23.
 */
public class DragScaleView extends View {
    //监听图片缩放
    private ScaleGestureDetector mScaleDetector;
    //监听图片移动
    private GestureDetector mGestureDetector;

    //当前的缩放比例
    private float mScaleFactor = 1.0f;

    public DragScaleView(Context context) {
        super(context);
    }

    public DragScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new SimpleScaleListenerImpl());
        mGestureDetector = new GestureDetector(context, new SimpleGestureListenerImpl());
    }

    private Paint bmpPaint = new Paint();
    //图片资源
    private Bitmap bmp;
    //图片的宽高
    private int bmpWidth, bmpHeight;

    public void setImageBitmap(Bitmap bitmap) {
        bmp = bitmap;
        bmpInit();
    }

    private void bmpInit() {
        bmpWidth = bmp.getWidth();
        bmpHeight = bmp.getHeight();

        initViewSize();

        invalidate();
    }

    public void setImageResource(int id) {
        bmp = BitmapFactory.decodeResource(getResources(), id);
        bmpInit();
    }

    //绘制图片的起始位置
    private float mPosX, mPosY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bmp == null) {
            return;
        }

        if (!hasGetViewSize) {
            initViewSize();
        }

        canvas.save();
        checkBounds();
        //以图片的中心为基点进行缩放
        canvas.scale(mScaleFactor, mScaleFactor, mPosX + bmpWidth / 2, mPosY + bmpHeight / 2);

        canvas.drawBitmap(bmp, mPosX, mPosY, bmpPaint);
        canvas.restore();
    }

//    private float lastX, lastY;

//    private static final int INVALID_POINTER_ID = -1;
//    private int mActivePointerId = INVALID_POINTER_ID;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        //双指缩放
        mScaleDetector.onTouchEvent(event);
        //单指移动
        mGestureDetector.onTouchEvent(event);

        return true;

        //也可以自己实现“单指移动图片”
        /*
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                mActivePointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                lastX = event.getX();
                lastY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                float currentX = MotionEventCompat.getX(event, pointerIndex);
                float currentY = MotionEventCompat.getY(event, pointerIndex);
                mPosX += (currentX - lastX);
                mPosY += (currentY - lastY);
                invalidate();
                lastX = currentX;
                lastY = currentY;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastX = MotionEventCompat.getX(event, newPointerIndex);
                    lastY = MotionEventCompat.getY(event, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
        }
        */

    }

    /**
     * 不能超出边界.
     * 原则是：图片较小时任意一条边都不能出了边界，图片较大任意一条边都不能进入边界。宽度和高度分别独立计算。
     */
    private void checkBounds() {
        if (mScaleFactor > widthScale) {
            //宽度方向已经填满
            mPosX = Math.min(mPosX, (mScaleFactor - 1) * (bmpWidth / 2));
            mPosX = Math.max(mPosX, viewWidth - bmpWidth - (mScaleFactor - 1) * (bmpWidth / 2));
        } else {
            mPosX = Math.max(mPosX, (mScaleFactor - 1) * (bmpWidth / 2));
            mPosX = Math.min(mPosX, viewWidth - bmpWidth - (mScaleFactor - 1) * (bmpWidth / 2));
        }

        if (mScaleFactor > heightScale) {
            //高度方向已经填满
            mPosY = Math.min(mPosY, (mScaleFactor - 1) * (bmpHeight / 2));
            mPosY = Math.max(mPosY, viewHeight - bmpHeight - (mScaleFactor - 1) * (bmpHeight / 2));
        } else {
            mPosY = Math.max(mPosY, (mScaleFactor - 1) * (bmpHeight / 2));
            mPosY = Math.min(mPosY, viewHeight - bmpHeight - (mScaleFactor - 1) * (bmpHeight / 2));
        }
    }

    private int viewWidth, viewHeight;
    //组件尺寸只需要获取一次
    private boolean hasGetViewSize;

    private void initViewSize() {
        viewWidth = getWidth();
        viewHeight = getHeight();

        if (viewWidth > 0 && viewHeight > 0) {
            hasGetViewSize = true;

            widthScale = 1.0f * viewWidth / bmpWidth;
            heightScale = 1.0f * viewHeight / bmpHeight;
            //初始缩放比例（使组件刚好铺满）
            mScaleFactor = Math.min(widthScale, heightScale);

            //初始时图片居中绘制
            mPosX = viewWidth / 2 - bmpWidth / 2;
            mPosY = viewHeight / 2 - bmpHeight / 2;
        }
    }

    /**
     * 宽度和高度放大多少倍时，刚好填满此方向的屏幕
     */
    private float widthScale, heightScale;

    //缩放
    private class SimpleScaleListenerImpl extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            //缩放倍数范围：0.3～3
            mScaleFactor = Math.max(0.3f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

    //移动
    private class SimpleGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mPosX -= distanceX;
            mPosY -= distanceY;

            invalidate();
            return true;
        }
    }
}