//package com.swsbt.secret.model.dialog;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.swsbt.secret.R;
//import com.swsbt.secret.dal.DiaryConfig;
//import com.swsbt.secret.model.MyDialog;
//import com.swsbt.secret.utility.AndroidConfigUtils;
//import com.swsbt.secret.view.LoginActivity;
//import com.swsbt.secret.view.MainActivity;
//
//import static com.swsbt.secret.utility.CommonMethods.QUERY_DATA;
//
//public class PasswordLoginDialog extends MyDialog {
//    private Context mContext;
//    public PasswordLoginDialog(Context context) {
//        super(context);
//        mContext = context;
//        init();
//    }
//
//    private EditText password;
//    private ImageView mGo;
//    private void init() {
//        setCancelable(false);
//        setContentView(R.layout.dialog_password_login);
//        initComponent();
//        others();
//    }
//
//    //各种控件操作
//    private void others(){
//        //Go按钮
//        mGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
//
//        //回车键进入
//        password.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER){
//                    login();
//                }
//                return false;
//            }
//        });
//    }
//
//    //控件声明
//    private void initComponent(){
//        mGo = (ImageView) findViewById(R.id.go);
//        password = (EditText)findViewById(R.id.password);
//    }
//
//    //登陆
//    private void login(){
//        String string = String.valueOf(password.getText());
//        String savedPassword = AndroidConfigUtils.getStringDefaultNull(mContext, DiaryConfig.SAVED_PASSWORD);
//        if (savedPassword == null || string.equals(savedPassword)){
//            dismiss();
//        }else {
//            Toast.makeText(mContext, R.string.loginFailed, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        System.exit(0);
//    }
//}
