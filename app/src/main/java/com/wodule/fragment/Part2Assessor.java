package com.wodule.fragment;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.wodule.R;
import com.wodule.activities.AssessorStartAct;
import com.wodule.adapter.ScoreAdapter;
import com.wodule.commonhelps.LakConst;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

import java.util.UUID;

/**
 * Created by Administrator on 5/9/2017.
 */

public class Part2Assessor extends BaseTFragment implements MediaPlayer.OnCompletionListener{
    private ImageView ivNext, ivDropdown, ivDropUp, ivPlay, qtPicture, ivPlayPre;
    private TextView lbComment, btnScore, lbHeader;
    private EditText edComment;
    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part2_asses, container, false);
        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        ivPlayPre = (ImageView) view.findViewById(R.id.ivPlayPre);
        ivDropdown = (ImageView) view.findViewById(R.id.ivDropdown);
        ivDropUp = (ImageView) view.findViewById(R.id.ivDropUp);
        lbHeader = (TextView) view.findViewById(R.id.lbHeader);
        lbComment = (TextView) view.findViewById(R.id.lbComment);
        btnScore = (TextView) view.findViewById(R.id.btnScore);
        edComment = (EditText) view.findViewById(R.id.edComment);
        qtPicture = (ImageView) view.findViewById(R.id.qtPicture);
        ivPlay = (ImageView) view.findViewById(R.id.ivPlay);
        setResultExam();
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                AssessorStartAct.storeresult.setPart_1_comment(edComment.getText().toString());
                startNewScreen(Part2Assessor.this, new Part3Assessor());
            }
        });
        ivPlayPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                back();
            }
        });
        ivDropUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbComment.setVisibility(View.VISIBLE);
                edComment.setVisibility(View.VISIBLE);
                ivDropUp.setVisibility(View.GONE);
                ivDropdown.setVisibility(View.VISIBLE);
            }
        });
        ivDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbComment.setVisibility(View.GONE);
                edComment.setVisibility(View.GONE);
                ivDropdown.setVisibility(View.GONE);
                ivDropUp.setVisibility(View.VISIBLE);
            }
        });
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScore();
            }
        });
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(ApiRequest.BASIC_URL + AssessorStartAct.storeresult.getPart_2());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    ivPlay.setImageResource(R.drawable.btn_pause);
                }else {
                    mediaPlayer.pause();
                    ivPlay.setImageResource(R.drawable.btn_play);
                }
            }
        });
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont(edComment);
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbHeader));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));
        FontUtils.setFont(lbComment);
        FontUtils.setFont(btnScore);
        return view;
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
    private void dialogScore(){
        final Dialog dialog_font = new Dialog(getActivity());
        dialog_font.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_font.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog_font.setTitle("SELECT SCORE");
        dialog_font.setContentView(R.layout.layout_list_scoredialog);
        GridView lv = (GridView)dialog_font.findViewById(R.id.list_font);
        ScoreAdapter adapter = new ScoreAdapter(getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog_font.dismiss();
                String score = (String) parent.getItemAtPosition(position);
                AssessorStartAct.storeresult.setPart_2_score(score);
            }
        });
        dialog_font.setCancelable(true);
        dialog_font.show();
    }
    private void setResultExam(){
        lbHeader.setText(AssessorStartAct.storeresult.getPart2_text());
        Glide.with(getActivity()).load(AssessorStartAct.storeresult.getPart2_url())
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(qtPicture);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        ivPlay.setImageResource(R.drawable.btn_play);
    }
}
