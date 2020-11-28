//package com.swsbt.secret.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.swsbt.secret.model.BaseActivity;
//import com.swsbt.secret.R;
//
//public class AboutActivity extends BaseActivity {
//
//    private TextView mThankYou;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
//        BUTTON();
//    }
//
//    private void BUTTON() {
//        mThankYou = (TextView)findViewById(R.id.thanks);
//        mThankYou.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AboutActivity.this,ThankYouActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}
