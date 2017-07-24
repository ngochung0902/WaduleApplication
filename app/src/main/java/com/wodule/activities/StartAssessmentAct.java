package com.wodule.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.fragment.IntructionFragment;
import com.wodule.object.ExamObject;
import com.wodule.utils.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 5/9/2017.
 */

public class StartAssessmentAct extends FragmentActivity {
    //    private FragmentManager mFragmentManager;
    private static final int NAVDRAWER_CLOSE_DELAY = 300;
    private BaseTFragment currentFragment;
    private ProgressDialog mProgressDialog;
    public static Fragment fragment;
    private boolean isnewUser;
    public static ExamObject examObj = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        examObj = new ExamObject();
        getComponents();
        getExamfile();
    }

    private void getComponents() {
        LakRun.setIsAssessment(getApplicationContext(), true);
//        if (mFragmentManager.getFragments() == null || mFragmentManager.getFragments().isEmpty()) {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override()
            public void run() {
                currentFragment = new IntructionFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fs = fm.beginTransaction();
                fs.add(R.id.fragmentHolder, currentFragment);
                fs.addToBackStack(null);
                fs.commit();

            }
        }, NAVDRAWER_CLOSE_DELAY);
//        } else {
//            currentFragment = getVisibleFragment();
//        }
    }

    public BaseTFragment getVisibleFragment() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getUserVisibleHint())
                return (BaseTFragment) fragment;
        }
        return null;
    }

    public void backToRoot() {
        for (int i = 1; i < getSupportFragmentManager()
                .getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void getExamfile() {
        showProgressDialog();
        Ion.with(getApplicationContext())
                .load("GET",ApiRequest.URL_GET_EXAM)// + LakRun.getUserId(getApplicationContext()))
//                .setHeader("Authorization","Bearer "+LakRun.getAccessToken(getApplicationContext()) )
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgressDialog();
                        if (e == null) {
                            if (!result.equalsIgnoreCase("No exam found")){
                                Gson gson = new Gson();
                                examObj = gson.fromJson(result, ExamObject.class);
//                    Type listType = new TypeToken<List<EventObject>>(){}.getType();
//                    listEvents = (List<EventObject>) gson.fromJson(response.body().toString(), listType);
                            }else {
                                LakRun.ShowpopupMessage(StartAssessmentAct.this, result);
                            }
                        }
                    }
                });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
