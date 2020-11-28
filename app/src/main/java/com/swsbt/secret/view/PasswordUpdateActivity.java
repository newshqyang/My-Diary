//package com.swsbt.secret.view;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.swsbt.secret.dal.DiaryConfig;
//import com.swsbt.secret.model.BaseActivity;
//import com.swsbt.secret.R;
//import com.swsbt.secret.utility.AndroidConfigUtils;
//
//import static com.swsbt.secret.utility.CommonMethods.DO_LOG;
//
//public class PasswordUpdateActivity extends BaseActivity {
//
//    private ImageView bingo;
//    private Button mOk;
//    private EditText passwordOld;
//    private EditText passwordNew;
//    private EditText passwordNewConfirm;
//
//    private LinearLayout main;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_password_update);
//        initComponent();
//        others();
//    }
//
//    //按钮监控
//    private void others(){
//        bingo.setVisibility(View.INVISIBLE);
//        mOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updatePassword();
//            }
//        });
//    }
//
//    //修改密码
//    private void updatePassword(){
//        String old_password = AndroidConfigUtils.getStringDefaultNull(this, DiaryConfig.SAVED_PASSWORD);
//        String old_password_edit = passwordOld.getText().toString();        //输入的旧密码
//        String new_password_edit = passwordNew.getText().toString();        //输入的新密码
//        String new_password_confirm_edit = passwordNewConfirm.getText().toString();     //输入的二次确认密码
//
//        if (passwordNew.getText().toString() == null || passwordNewConfirm.getText().toString() == null) {
//            return;
//        }
//
//        // 两次新密码输入不一致
//        if (!new_password_edit.equals(new_password_confirm_edit)) {
//            Toast.makeText(this,R.string.password_update_warning3,Toast.LENGTH_SHORT).show();
//            DO_LOG(this,6,"none");
//            return;
//        }
//
//        if (old_password == null) {
//            old_password = "";
//        }
//
//        // 输入的原密码与保存的原密码不一致
//        if (!old_password.equals(old_password_edit)) {
//            Toast.makeText(this,R.string.password_update_warning,Toast.LENGTH_SHORT).show();
//            DO_LOG(PasswordUpdateActivity.this,6,"none");
//            return;
//        }
//
//        // 修改成功
//        AndroidConfigUtils.saveString(this, DiaryConfig.SAVED_PASSWORD, new_password_confirm_edit);
//        main.setVisibility(View.INVISIBLE);
//        bingo.setVisibility(View.VISIBLE);
//        DO_LOG(PasswordUpdateActivity.this,7,"none");
//    }
//    //初始化控件
//    private void initComponent() {
//        bingo = findViewById(R.id.bingo);
//        mOk = findViewById(R.id.ok);
//        passwordOld = findViewById(R.id.password_old);
//        passwordNew = findViewById(R.id.password_new);
//        passwordNewConfirm = findViewById(R.id.password_new_confirm);
//        main = findViewById(R.id.main);
//    }
//}
