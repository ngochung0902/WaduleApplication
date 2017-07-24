package com.wodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.FontUtils;

/**
 * Created by Administrator on 5/9/2017.
 */

public class IntructionFragment extends BaseTFragment {
    private TextView btnStartAssess;
    private LinearLayout ll_group_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        btnStartAssess = (TextView) view.findViewById(R.id.btnStartAssess);
        ll_group_btn = (LinearLayout) view.findViewById(R.id.ll_group_btn);
        LakRun.setLayoutView(ll_group_btn,LakRun.GetWidthDevice(getActivity()),LakRun.GetHeightDevice(getActivity())/2);
        LakRun.setLayoutView(btnStartAssess,LakRun.GetWidthDevice(getActivity())/2,LakRun.GetWidthDevice(getActivity())/4);
        btnStartAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startNewScreen(IntructionFragment.this, new Part1());
            }
        });
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));
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
