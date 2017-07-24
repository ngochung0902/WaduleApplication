package com.wodule.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.adapter.HistoryAdapterList;


public class HistoryExamActivity extends AppCompatActivity {
    private static final String TAG = HistoryExamActivity.class.getSimpleName();
    private String mTitle;
    private TextView lbTitle;
    private ListView listHistory;
    HistoryAdapterList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_history);
        lbTitle = (TextView) findViewById(R.id.lbTitle);
        mTitle = getIntent().getStringExtra("title");
        listHistory = (ListView) findViewById(R.id.list_history);
        lbTitle.setText(mTitle);

        adapter =  new HistoryAdapterList(getApplicationContext());
        listHistory.setAdapter(adapter);
    }
}
