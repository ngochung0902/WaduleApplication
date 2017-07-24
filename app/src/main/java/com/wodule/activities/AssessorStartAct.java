package com.wodule.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.fragment.Part1Assessor;
import com.wodule.fragment.PartEnd;
import com.wodule.object.ResponError;
import com.wodule.object.ResponResult;
import com.wodule.utils.ApiRequest;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 5/9/2017.
 */

public class AssessorStartAct extends FragmentActivity {
    private static final int NAVDRAWER_CLOSE_DELAY = 300;
    private BaseTFragment currentFragment;
    public static Fragment fragment;
    public static ResponResult storeresult;
    protected ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        downloadResult();

    }

    private void getComponents() {
        LakRun.setIsAssessment(getApplicationContext(), true);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override()
            public void run() {
                currentFragment = new Part1Assessor();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fs = fm.beginTransaction();
                fs.add(R.id.fragmentHolder, currentFragment);
                fs.addToBackStack(null);
                fs.commit();

            }
        }, NAVDRAWER_CLOSE_DELAY);
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
    private void downloadResult(){
        pDialog = new ProgressDialog(AssessorStartAct.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        storeresult = new ResponResult();
        Ion.with(getApplicationContext())
                .load("GET", ApiRequest.URL_RESULT_DOWNLOAD)
                .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getApplicationContext()))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        pDialog.dismiss();
                        if (e == null){
                            Log.e("ExamActivity", "result:" +result);
                            Gson gson = new Gson();
                            if (result.contains("error")){
                                ResponError error = gson.fromJson(result, ResponError.class);
                                LakRun.showToast(getApplicationContext(), error.getError());
                            }else {
                                storeresult = gson.fromJson(result,ResponResult.class);
                                if (storeresult != null)
                                    getComponents();
                                else {
                                    LakRun.showToast(getApplicationContext(), "Exam not found.");
                                    finish();
                                }
                            }
                        }
                    }
                });
    }
}
