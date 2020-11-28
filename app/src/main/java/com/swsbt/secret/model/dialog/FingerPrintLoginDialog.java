//package com.swsbt.secret.model.dialog;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.swsbt.secret.R;
//import com.swsbt.secret.model.MyDialog;
//
//public class FingerPrintLoginDialog extends MyDialog {
//
//    private Context mContext;
//    public FingerPrintLoginDialog(Context context) {
//        super(context);
//        mContext = context;
//        init();
//    }
//
//
//    private ImageView mFingerPrint;
//    private TextView mFingerPrintBoard;
//    private Button mGotoPassword;
//    private void init() {
//        setCancelable(false);
//        setContentView(R.layout.dialog_finger_print_login);
//        mFingerPrint = (ImageView)findViewById(R.id.finger_print);
//        mFingerPrintBoard = (TextView)findViewById(R.id.finger_print_board);
//        mGotoPassword = (Button)findViewById(R.id.goto_password);
//        //如果用户没有开启指纹解锁，直接跳转到指令解锁页面
//        //判断是否开启指纹解锁如果开启，自动跳转到指纹解锁页面
//        //声明指纹管理
//        FingerprintManagerCompat fingerprintManagerCompat;
//        fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
//        fingerprintManagerCompat.authenticate(null, 0, null, new FingerAuthenticateCallBack(),null);
//        mGotoPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goPasswordLoginDialog();
//            }
//        });
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what) {
//                case 1:
//                    goPasswordLoginDialog();
//                    break;
//                case 2:
//                    dismiss();
//                    break;
//            }
//        }
//    };
//
//    /*
//    跳转到指令解锁界面
//     */
//    private void goPasswordLoginDialog(){
//        dismiss();
//        new PasswordLoginDialog(mContext).show();
//    }
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
//                    mHandler.sendEmptyMessage(1);
//                }
//            }).start();
//        }
//
//        //当验证失败时会调用此函数
//        @Override
//        public void onAuthenticationFailed(){
//            mFingerPrint.setImageDrawable(mContext.getResources().getDrawable(R.drawable.finger_print_error, mContext.getTheme()));
//            mFingerPrintBoard.setText("无法识别");
//        }
//
//        //当验证成功时调用此函数
//        @Override
//        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result){
//            mFingerPrint.setImageDrawable(mContext.getResources().getDrawable(R.drawable.finger_print_success, mContext.getTheme()));
//            mFingerPrintBoard.setText("验证成功");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mHandler.sendEmptyMessage(2);
//                }
//            }).start();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        System.exit(0);
//    }
//}
