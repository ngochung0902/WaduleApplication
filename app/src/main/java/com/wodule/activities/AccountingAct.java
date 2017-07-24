package com.wodule.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wodule.R;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.fragment.Accounting1Fragment;

import java.util.List;

/**
 * Created by Administrator on 5/9/2017.
 */

public class AccountingAct extends FragmentActivity {
//    private FragmentManager mFragmentManager;
    private static final int NAVDRAWER_CLOSE_DELAY = 300;
    private BaseTFragment currentFragment;

    public static Fragment fragment;
    private boolean isnewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        mFragmentManager = getSupportFragmentManager();
//        isnewUser = getIntent().getBooleanExtra("isnewUser", false);
        getComponents();
    }
    private void getComponents() {
        LakRun.setIsAssessment(getApplicationContext(),true);
//        if (mFragmentManager.getFragments() == null || mFragmentManager.getFragments().isEmpty()) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override()
                public void run() {
                        currentFragment = new Accounting1Fragment();
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
            if (fragment != null && fragment.getUserVisibleHint() )
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
        if (fm.getBackStackEntryCount()== 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
