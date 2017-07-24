package com.wodule.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakRun;

public class MainActivity extends AppCompatActivity {
    private TextView lbExamhistory, lbCalender, lbStartExam;
    private ImageView iconAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lbExamhistory = (TextView) findViewById(R.id.lbExamhistory);
        lbCalender = (TextView) findViewById(R.id.lbCalender);
        lbStartExam = (TextView) findViewById(R.id.lbStartExam);
        iconAvatar = (ImageView) findViewById(R.id.iconAvatar);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        iconAvatar.setImageBitmap(LakRun.getRoundedCornerBitmap(bm, 15));

        lbExamhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        HistoryExamActivity.class);
                intent.putExtra("title", "EXAM HISTORY");
                startActivity(intent);
            }
        });
        lbCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        CalendarExamActivity.class);
                intent.putExtra("title", "CALENDAR OF EXAM");
                startActivity(intent);
            }
        });
        lbStartExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        HomeActivity.class);
                intent.putExtra("isnewUser", false);
                startActivity(intent);
            }
        });
    }
}
