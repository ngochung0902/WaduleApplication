package com.wodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.FontUtils;

/**
 * Created by Administrator on 5/9/2017.
 */

public class PartEnd extends BaseTFragment {
    private ImageView ivAvatar;
    private TextView btnHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part_end, container, false);
        btnHome = (TextView) view.findViewById(R.id.btnHome);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (LakRun.getIsAssessment(getActivity())){
//                    Intent intent = new Intent(getActivity(),
//                            AssessorActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                }else {
//                    Intent intent = new Intent(getActivity(),
//                            MainAssessmentActivity.class);
//                    startActivity(intent);
                    getActivity().finish();
//                }
            }
        });

        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbQuestion));
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbHeader));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));
        FontUtils.setFont((TextView)view.findViewById(R.id.btnHome));
        FontUtils.setFont((TextView)view.findViewById(R.id.btnNextExam));
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
