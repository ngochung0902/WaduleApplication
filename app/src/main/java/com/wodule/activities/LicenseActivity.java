package com.wodule.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.utils.FontUtils;

public class LicenseActivity extends AppCompatActivity {
    private TextView lbDisagree, lbAgree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        lbDisagree = (TextView) findViewById(R.id.lbDisagree);
        lbDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lbAgree = (TextView) findViewById(R.id.lbAgree);

        setProperties();

        lbAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    private void setProperties(){
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont(lbDisagree);
        FontUtils.setFont(lbAgree);
        FontUtils.setFont((TextView)findViewById(R.id.tvContent));
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)findViewById(R.id.lbLicense));
    }
}
