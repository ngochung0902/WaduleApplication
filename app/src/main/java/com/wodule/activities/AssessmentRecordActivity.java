package com.wodule.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.adapter.HistoryAdapterList;

public class AssessmentRecordActivity extends AppCompatActivity {
    private ImageView ivNext, ivBack;
    private TextView tabToday, tabHistory;
    private ListView listHistory;
    HistoryAdapterList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_record);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tabHistory = (TextView) findViewById(R.id.tabHistory) ;
        tabToday = (TextView) findViewById(R.id.tabToday) ;
        listHistory = (ListView) findViewById(R.id.list_history);

        adapter =  new HistoryAdapterList(getApplicationContext());
        listHistory.setAdapter(adapter);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabToday.setBackgroundResource(R.drawable.border_rect_white_tab);
                tabHistory.setBackgroundResource(R.drawable.border_rect_green_tab);
                listHistory.setVisibility(View.GONE);
            }
        });
        tabHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabHistory.setBackgroundResource(R.drawable.border_rect_white_tab);
                tabToday.setBackgroundResource(R.drawable.border_rect_green_tab);
                listHistory.setVisibility(View.VISIBLE);
            }
        });
    }
}
