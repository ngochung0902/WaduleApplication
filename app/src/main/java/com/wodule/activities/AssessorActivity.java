package com.wodule.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.object.UserObject;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class AssessorActivity extends AppCompatActivity {
    private TextView lbAssessmentRecord, lbCalender, lbAccounting, lbStartAssessment;
    private ImageView iconAvatar, iconStart, iconCalendar, iconAccount, iconBag, btnEdit;
    private TextView lbName, lbSchool, lbSex, lbAge, tvIdExam, lbLogout;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor);
        lbAssessmentRecord = (TextView) findViewById(R.id.lbAssessmentRecord);
        lbCalender = (TextView) findViewById(R.id.lbCalender);
        lbAccounting = (TextView) findViewById(R.id.lbAccounting);
        lbStartAssessment = (TextView) findViewById(R.id.lbStartAssessment);
        iconAvatar = (ImageView) findViewById(R.id.iconAvatar);
        iconBag = (ImageView) findViewById(R.id.iconBag);
        btnEdit = (ImageView) findViewById(R.id.btnEdit);
        iconCalendar = (ImageView) findViewById(R.id.iconCalendar);
        iconAccount = (ImageView) findViewById(R.id.iconAccount);
        iconStart = (ImageView) findViewById(R.id.iconStart);
        lbName = (TextView) findViewById(R.id.lbName);
        lbSchool = (TextView) findViewById(R.id.lbSchool);
        lbSex = (TextView) findViewById(R.id.lbSex);
        lbAge = (TextView) findViewById(R.id.lbAge);
        tvIdExam = (TextView) findViewById(R.id.tvIdExam);
        lbLogout = (TextView) findViewById(R.id.lbLogout);

        setProperties();
        if (LakRun.getIsFirstLogin(getApplicationContext())) {
            lbName.setText("N/A");
            tvIdExam.setText("1");
            lbSchool.setText("N/A");
            lbSex.setText("Sex: " + "N/A");
            lbAge.setText("Age: " + "N/A");
        } else {
            if (LakRun.isNetworkAvailable(getApplicationContext()))
                getProfile();
            else {
                LakRun.ShowpopupMessage(AssessorActivity.this, getString(R.string.check_network));
                loadDataStorage();
            }
        }
        lbAccounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessorActivity.this,
                        AccountingAct.class);
//                intent.putExtra("title", "ASSESSMENT HISTORY");
                startActivity(intent);
            }
        });
        lbCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessorActivity.this,
                        CalendarExamActivity.class);
                intent.putExtra("title", "CALENDAR OF ASSESSMENT");
                startActivity(intent);
            }
        });
        lbStartAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessorActivity.this,
                        AssessorStartAct.class);
                startActivity(intent);
            }
        });
        lbAssessmentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessorActivity.this,
                        HistoryAssessmentActivity.class);
                intent.putExtra("title", "ASSESSMENT HISTORY");
                startActivity(intent);
            }
        });
        lbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LakRun.setAccessToken(getApplicationContext(),"");
                LakRun.setIsLogin(getApplicationContext(), false);
                LakRun.setIsEdit(getApplicationContext(), false);
                LakConst.pictureFile = null;
                LakConst.bmAvatar = null;
                Intent intent = new Intent(AssessorActivity.this,
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LakRun.setIsEdit(getApplicationContext(), true);
                Intent intent = new Intent(AssessorActivity.this,
                        HomeActivity.class);
                intent.putExtra("isnewUser", true);
                startActivity(intent);
            }
        });
    }

    private void setProperties() {
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFonts((LinearLayout) findViewById(R.id.ll_group_profile));
        FontUtils.loadFont(getApplicationContext(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView) findViewById(R.id.lbName));
        FontUtils.setFont((TextView) findViewById(R.id.tvIdExam));
        FontUtils.setFont(lbAccounting);
        FontUtils.setFont(lbCalender);
        FontUtils.setFont(lbStartAssessment);
        FontUtils.setFont(lbAssessmentRecord);
        iconAvatar.setScaleType(ImageView.ScaleType.FIT_XY);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
//        iconAvatar.setImageBitmap(LakRun.getRoundedCornerBitmap(bm, 15));
        LakRun.setLayoutView(iconAvatar, LakRun.GetWidthDevice(getApplicationContext())*2/5, LakRun.GetWidthDevice(getApplicationContext())*2/5);
        LakRun.setLayoutView(iconBag, LakRun.GetWidthDevice(getApplicationContext()) / 8, LakRun.GetWidthDevice(getApplicationContext()) / 8);
        LakRun.setLayoutView(iconCalendar, LakRun.GetWidthDevice(getApplicationContext()) / 8, LakRun.GetWidthDevice(getApplicationContext()) / 8);
        LakRun.setLayoutView(iconAccount, LakRun.GetWidthDevice(getApplicationContext()) / 8, LakRun.GetWidthDevice(getApplicationContext()) / 8);
        LakRun.setLayoutView(iconStart, LakRun.GetWidthDevice(getApplicationContext()) / 8, LakRun.GetWidthDevice(getApplicationContext()) / 8);
    }
    private void getProfile() {
        pDialog = new ProgressDialog(AssessorActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Ion.with(getApplicationContext())
                .load(ApiRequest.URL_GETPROFILE)
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
                            Gson gson = new Gson();
                            UserObject userObj = new UserObject();
                            try {
                                JSONObject json = new JSONObject(result);
                                if (json.toString().contains("error")){
                                    LakRun.showToast(getApplicationContext(),json.get("error").toString() );
                                    LakRun.setAccessToken(getApplicationContext(),"");
                                    LakRun.setIsLogin(getApplicationContext(), false);
                                    LakRun.setIsEdit(getApplicationContext(), false);
                                    LakConst.pictureFile = null;
                                    LakConst.bmAvatar = null;
                                    Intent intent = new Intent(AssessorActivity.this,
                                            LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    LakRun.setUserObject(getApplicationContext(), json.getJSONObject("user").toString());
                                    userObj = gson.fromJson(json.getJSONObject("user").toString(), UserObject.class);
                                    if (userObj.getFirst_name().equalsIgnoreCase("temp")) {
                                        LakRun.setIsEdit(getApplicationContext(), true);
                                        Intent intent = new Intent(AssessorActivity.this,
                                                HomeActivity.class);
                                        intent.putExtra("isnewUser", true);
                                        startActivity(intent);
                                    } else {
                                        Glide.with(getApplicationContext()).load("http://wodule.io/" + String.valueOf(userObj.getPicture()))
                                                .asBitmap()
                                                .fitCenter()
                                                .signature(new StringSignature(UUID.randomUUID().toString()))
//                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .into(iconAvatar);
                                        LakRun.setUsername(getApplicationContext(), userObj.getUser_name());
                                        LakRun.setUserId(getApplicationContext(), String.valueOf(userObj.getId()));
                                        lbName.setText(userObj.getFirst_name() + " " + userObj.getMiddle_name() + " " + userObj.getLast_name());
                                        tvIdExam.setText(String.valueOf(userObj.getId()));
                                        lbSchool.setText(userObj.getStudent_class());
                                        lbSex.setText("Sex: " + userObj.getGender());
                                        String[] strdate = userObj.getDate_of_birth().split("-");
                                        lbAge.setText("Age: " + LakRun.getAge(Integer.parseInt(strdate[0]), Integer.parseInt(strdate[1]), Integer.parseInt(strdate[2])));
                                    }
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
//                    Type listType = new TypeToken<List<EventObject>>(){}.getType();
//                    listEvents = (List<EventObject>) gson.fromJson(response.body().toString(), listType);
                        }
                    }
                });
    }

    private void loadDataStorage() {
        Gson gson = new Gson();
        UserObject userObj = new UserObject();
        userObj = gson.fromJson(LakRun.getUserObject(getApplicationContext()), UserObject.class);
        if (userObj.getFirst_name().equalsIgnoreCase("temp")) {
            // call update method...
        } else {
            Glide.with(getApplicationContext()).load("http://wodule.io/" + String.valueOf(userObj.getPicture()))
                    .asBitmap()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iconAvatar);
            LakRun.setUsername(getApplicationContext(), userObj.getUser_name());
            LakRun.setUserId(getApplicationContext(), String.valueOf(userObj.getId()));
            lbName.setText(userObj.getFirst_name() + " " + userObj.getMiddle_name() + " " + userObj.getLast_name());
            tvIdExam.setText(String.valueOf(userObj.getId()));
            lbSchool.setText(userObj.getStudent_class());
            lbSex.setText("Sex: " + userObj.getGender());
            String[] strdate = userObj.getDate_of_birth().split("-");
            lbAge.setText("Age: " + LakRun.getAge(Integer.parseInt(strdate[0]), Integer.parseInt(strdate[1]), Integer.parseInt(strdate[2])));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (LakRun.getIsReload(getApplicationContext())){
            LakRun.setIsReload(getApplicationContext(), false);
            if (LakRun.isNetworkAvailable(getApplicationContext())){
                getProfile();
            }
        }
    }
}
