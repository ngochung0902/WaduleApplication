package com.wodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wodule.R;
import com.wodule.custom.BaseTFragment;

/**
 * Created by Administrator on 5/9/2017.
 */

public class Accounting1Fragment extends BaseTFragment {
    private ImageView ivNext, ivBack;
    private RelativeLayout rl_tab_week;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account1, container, false);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        rl_tab_week = (RelativeLayout) view.findViewById(R.id.rl_tab_week);
        rl_tab_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startNewScreen(Accounting1Fragment.this, new Accounting2Fragment());
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {

    }
}
