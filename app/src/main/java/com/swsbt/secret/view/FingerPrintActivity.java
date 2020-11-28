//package com.swsbt.secret.view;
//
//import android.content.Intent;
//import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.swsbt.secret.model.BaseActivity;
//import com.swsbt.secret.R;
//
//import java.io.File;
//
//public class FingerPrintActivity extends BaseActivity {
//
//    private ImageView mFingerPrint;
//    private TextView mFingerPrintBoard;
//    private Button mGotoPassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_finger_print);
//        mFingerPrint = (ImageView)findViewById(R.id.finger_print);
//        mFingerPrintBoard = (TextView)findViewById(R.id.finger_print_board);
//        mGotoPassword = (Button)findViewById(R.id.goto_password);
//        //如果用户没有开启指纹解锁，直接跳转到指令解锁页面
//        //判断是否开启指纹解锁如果开启，自动跳转到指纹解锁页面
//        String fingerPrintOnFlagPath = "/data/data/com.swsbt.secret/fingerPrintOn";
//        File file = new File(fingerPrintOnFlagPath);
//        if (!file.exists()){
//            INTENT();
//        }else {
//            //声明指纹管理
//            FingerprintManagerCompat fingerprintManagerCompat;
//            fingerprintManagerCompat = FingerprintManagerCompat.from(this);
//            fingerprintManagerCompat.authenticate(null, 0, null, new FingerAuthenticateCallBack(),null);
//            usePassword();
//        }
//    }
//
//    private void INTENT(){
//        //跳转到指令解锁界面
//        Intent intent = new Intent(FingerPrintActivity.this, LoginActivity.class);
//        intent.putExtra("fingerPrint","normal");
//        finish();
//        startActivity(intent);
//    }
//
//    private void usePassword(){
//        mGotoPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                INTENT();
//            }
//        });
//    }
//
//    class FingerAuthenticateCallBack extends FingerprintManagerCompat.AuthenticationCallback{
//        private static final String TAG = "FingerAuthenticateCallBack";
//
//        //当出现错误次数超过5次时回调此函数
//        @Override
//        public void onAuthenticationError(int errMsgId,CharSequence errString){
//            mFingerPrintBoard.setText("失败次数过多，请使用指令解锁");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    INTENT();
//                }
//            }).start();
//        }
//
//        //当验证失败时会调用此函数
//        @Override
//        public void onAuthenticationFailed(){
//            mFingerPrint.setImageDrawable(getResources().getDrawable(R.drawable.finger_print_error,getTheme()));
//            mFingerPrintBoard.setText("无法识别");
//        }
//
//        //当验证成功时调用此函数
//        @Override
//        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result){
//            mFingerPrint.setImageDrawable(getResources().getDrawable(R.drawable.finger_print_success,getTheme()));
//            mFingerPrintBoard.setText("验证成功");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent(FingerPrintActivity.this, MainActivity.class);
//                    intent.putExtra("fingerPrint","succeed");
//                    startActivity(intent);
//                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//                    finish();
//                }
//            }).start();
//        }
//    }
//
//    @Override
//    public void onBackPressed(){
//        finish();
//        System.exit(0);
//    }
//}
