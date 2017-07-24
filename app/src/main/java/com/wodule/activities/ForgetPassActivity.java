package com.wodule.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.utils.FontUtils;


public class ForgetPassActivity extends AppCompatActivity {
    private static final String TAG = ForgetPassActivity.class.getSimpleName();
    TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        btnSubmit = (TextView) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setProperties();
    }
    private void setProperties(){
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFonts((LinearLayout) findViewById(R.id.ll_group_center));
        FontUtils.setFont((TextView)findViewById(R.id.tvContent));
        FontUtils.setFont((TextView)findViewById(R.id.tvBottom));
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)findViewById(R.id.lbTitle));
        FontUtils.setFont(btnSubmit);

    }
}
