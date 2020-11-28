//package com.swsbt.secret.model.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.Window;
//import android.view.WindowManager;
//
//public class BottomFullDialog extends Dialog {
//
//    public BottomFullDialog(Context context) {
//        super(context);
//    }
//
//    public BottomFullDialog(Context context, int themeResId) {
//        super(context, themeResId);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//    }
//
//
//    @SuppressWarnings("deprecation")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setGravity(Gravity.TOP);//设置显示在底部
//        WindowManager windowManager=getWindow().getWindowManager();
//        Display display= windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
//        layoutParams.width = display.getWidth();//设置Dialog的宽度为屏幕宽度
//        layoutParams.height = display.getHeight();
//        getWindow().setAttributes(layoutParams);
//    }
//}
