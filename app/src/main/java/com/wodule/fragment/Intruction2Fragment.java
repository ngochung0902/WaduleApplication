package com.wodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.custom.BaseTFragment;

/**
 * Created by Administrator on 5/9/2017.
 */

public class Intruction2Fragment extends BaseTFragment {
    private ImageView ivNext, ivBack;
    private TextView btnNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction2, container, false);
//        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        btnNext = (TextView) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startNewScreen(Intruction2Fragment.this, new Part1());
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
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
