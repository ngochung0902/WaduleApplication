package com.wodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.adapter.AccountAdapterList;
import com.wodule.commonhelps.LakConst;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.FontUtils;

/**
 * Created by Administrator on 5/9/2017.
 */

public class AccountFragment extends BaseTFragment {
    private ImageView ivBack;
    private ListView listHistoryAccount;
    AccountAdapterList adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_assessment, container, false);
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        listHistoryAccount = (ListView) view.findViewById(R.id.listHistoryAccount);

        adapter =  new AccountAdapterList(getActivity());
        listHistoryAccount.setAdapter(adapter);

        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_group_header));
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
