package com.wodule.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.adapter.HistoryAdapterList;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.object.HistoryExam;
import com.wodule.object.UserObject;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.List;


public class HistoryAssessmentActivity extends AppCompatActivity {
    private static final String TAG = HistoryAssessmentActivity.class.getSimpleName();
    private String mTitle;
    private TextView lbTitle, lbNoresult;
    private ListView listHistory;
    private ImageView ivBack;
    List<HistoryExam> arrList = null;
    HistoryAdapterList adapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_history);
        lbTitle = (TextView) findViewById(R.id.lbTitle);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        lbNoresult = (TextView) findViewById(R.id.lbNoresult);
        listHistory = (ListView) findViewById(R.id.list_history);
        mTitle = getIntent().getStringExtra("title");

        lbTitle.setText(mTitle);
        setProperties();
        getResultHistory();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setProperties() {
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFonts((RelativeLayout) findViewById(R.id.rl_group_header));
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont(lbTitle);
    }

    private void getResultHistory() {
        pDialog = new ProgressDialog(HistoryAssessmentActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Ion.with(getApplicationContext())
                .load(ApiRequest.URL_STORE_RESULT)
                .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getApplicationContext()))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (pDialog != null) {
                            pDialog.dismiss();
                            pDialog.cancel();
                        }
                        if (e == null) {
                            Log.e("HistoryAssessment", "Result:" +result);
                            if (result.contains("error")){
                                lbNoresult.setVisibility(View.VISIBLE);
                                listHistory.setVisibility(View.GONE);
                            }else {
                                lbNoresult.setVisibility(View.GONE);
                                listHistory.setVisibility(View.VISIBLE);
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<HistoryExam>>() {
                                }.getType();
                                arrList = (List<HistoryExam>) gson.fromJson(result.toString(), listType);
                                adapter = new HistoryAdapterList(getApplicationContext(), arrList);
                                listHistory.setAdapter(adapter);
                            }
                        }
                    }
                });
    }
}
