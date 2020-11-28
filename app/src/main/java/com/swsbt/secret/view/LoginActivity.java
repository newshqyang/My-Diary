//package com.swsbt.secret.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.swsbt.secret.model.BaseActivity;
//import com.swsbt.secret.R;
//
//import static com.swsbt.secret.utility.CommonMethods.QUERY_DATA;
//
//public class LoginActivity extends BaseActivity {
//
//    private EditText password;
//    private ImageView mGo;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_password);
//        DECLARATION_CONTROL();
//        DO();
//    }
//
//    //各种控件操作
//    private void DO(){
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
//    private void DECLARATION_CONTROL(){
//        mGo = (ImageView) findViewById(R.id.go);
//        password = (EditText)findViewById(R.id.password);
//    }
//
//    //登陆
//    private void login(){
//        String string = String.valueOf(password.getText());
//        String key = QUERY_DATA(LoginActivity.this,"article","login_password",
//                "id=?","root","password");
//        if (string.equals(key)){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            Toast.makeText(LoginActivity.this,R.string.loginFailed,Toast.LENGTH_SHORT).show();
//            password.setText("");
//        }
//    }
//
//    @Override
//    public void onBackPressed(){
//        finish();
//        System.exit(0);
//    }
//}
