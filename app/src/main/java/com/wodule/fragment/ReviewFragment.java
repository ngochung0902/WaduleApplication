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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.activities.AssessorStartAct;
import com.wodule.adapter.ScoreAdapter;
import com.wodule.commonhelps.LakConst;
import com.wodule.custom.BaseTFragment;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;

/**
 * Created by Administrator on 5/9/2017.
 */

public class ReviewFragment extends BaseTFragment implements MediaPlayer.OnCompletionListener{
    private TextView tvSubmit, tvTime1, tvTime2, tvTime3, tvTime4;
    private TextView tvExamId, title_part1, title_part2, title_part3, title_part4;
    private TextView scorePart1, scorePart2, scorePart3, scorePart4;
    private TextView btnScore1, btnScore2, btnScore3, btnScore4;
    private ImageView btnAudioP4, btnAudioP3, btnAudioP2, btnAudioP1;

    private MediaPlayer mediaPlayerPart1;
    private MediaPlayer mediaPlayerPart2;
    private MediaPlayer mediaPlayerPart3;
    private MediaPlayer mediaPlayerPart4;
    private int durationsPart1, durationsPart2, durationsPart3, durationsPart4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_assessment_overview, container, false);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);
        tvTime1 = (TextView) view.findViewById(R.id.tvTime1);
        tvTime2 = (TextView) view.findViewById(R.id.tvTime2);
        tvTime3 = (TextView) view.findViewById(R.id.tvTime3);
        tvTime4 = (TextView) view.findViewById(R.id.tvTime4);
        tvExamId = (TextView) view.findViewById(R.id.tvExamId);
        title_part1 = (TextView) view.findViewById(R.id.title_part1);
        title_part2 = (TextView) view.findViewById(R.id.title_part2);
        title_part3 = (TextView) view.findViewById(R.id.title_part3);
        title_part4 = (TextView) view.findViewById(R.id.title_part4);
        scorePart1 = (TextView) view.findViewById(R.id.scorePart1);
        scorePart2 = (TextView) view.findViewById(R.id.scorePart2);
        scorePart3 = (TextView) view.findViewById(R.id.scorePart3);
        scorePart4 = (TextView) view.findViewById(R.id.scorePart4);
        btnScore1 = (TextView) view.findViewById(R.id.btnScore1);
        btnScore2 = (TextView) view.findViewById(R.id.btnScore2);
        btnScore3 = (TextView) view.findViewById(R.id.btnScore3);
        btnScore4 = (TextView) view.findViewById(R.id.btnScore4);
        btnAudioP1 = (ImageView) view.findViewById(R.id.btnAudioP1);
        btnAudioP2 = (ImageView) view.findViewById(R.id.btnAudioP2);
        btnAudioP3 = (ImageView) view.findViewById(R.id.btnAudioP3);
        btnAudioP4 = (ImageView) view.findViewById(R.id.btnAudioP4);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudios(mediaPlayerPart1);
                stopAudios(mediaPlayerPart2);
                stopAudios(mediaPlayerPart3);
                stopAudios(mediaPlayerPart4);
                startNewScreen(ReviewFragment.this, new AccountFragment());
            }
        });
        setProperties(view);
        setExamResult();
        btnScore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScore(1);
            }
        });
        btnScore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScore(2);
            }
        });
        btnScore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScore(3);
            }
        });
        btnScore4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScore(4);
            }
        });
        btnAudioP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAudios(mediaPlayerPart2, tvTime2, btnAudioP2);
                resetAudios(mediaPlayerPart3, tvTime3, btnAudioP3);
                resetAudios(mediaPlayerPart4, tvTime4, btnAudioP4);
                try {
                    mediaPlayerPart1.setDataSource(ApiRequest.BASIC_URL + AssessorStartAct.storeresult.getPart_1());
                    mediaPlayerPart1.prepare();
                    durationsPart1 = mediaPlayerPart1.getDuration();
                    tvTime1.setText(milisecondToString(durationsPart1,TYPE.NUM));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mediaPlayerPart1.isPlaying()){
                    mediaPlayerPart1.start();
                    btnAudioP1.setVisibility(View.GONE);
                    tvTime1.setVisibility(View.VISIBLE);
                }else {
                    mediaPlayerPart1.pause();
                    tvTime1.setVisibility(View.GONE);
                    btnAudioP1.setVisibility(View.VISIBLE);
                }
            }
        });
        btnAudioP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAudios(mediaPlayerPart1, tvTime2, btnAudioP1);
                resetAudios(mediaPlayerPart3, tvTime3, btnAudioP3);
                resetAudios(mediaPlayerPart4, tvTime4, btnAudioP4);
                try {
                    mediaPlayerPart2.setDataSource(ApiRequest.BASIC_URL + AssessorStartAct.storeresult.getPart_2());
                    mediaPlayerPart2.prepare();
                    durationsPart2 = mediaPlayerPart1.getDuration();
                    tvTime2.setText(milisecondToString(durationsPart2,TYPE.NUM));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mediaPlayerPart2.isPlaying()){
                    mediaPlayerPart2.start();
                    btnAudioP2.setVisibility(View.GONE);
                    tvTime2.setVisibility(View.VISIBLE);
                }else {
                    mediaPlayerPart2.pause();
                    tvTime2.setVisibility(View.GONE);
                    btnAudioP2.setVisibility(View.VISIBLE);
                }
            }
        });
        btnAudioP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAudios(mediaPlayerPart2, tvTime2, btnAudioP2);
                resetAudios(mediaPlayerPart1, tvTime3, btnAudioP1);
                resetAudios(mediaPlayerPart4, tvTime4, btnAudioP4);
                try {
                    mediaPlayerPart3.setDataSource(ApiRequest.BASIC_URL + AssessorStartAct.storeresult.getPart_3());
                    mediaPlayerPart3.prepare();
                    durationsPart3 = mediaPlayerPart1.getDuration();
                    tvTime3.setText(milisecondToString(durationsPart3,TYPE.NUM));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mediaPlayerPart3.isPlaying()){
                    mediaPlayerPart3.start();
                    btnAudioP3.setVisibility(View.GONE);
                    tvTime3.setVisibility(View.VISIBLE);
                }else {
                    mediaPlayerPart3.pause();
                    tvTime3.setVisibility(View.GONE);
                    btnAudioP3.setVisibility(View.VISIBLE);
                }
            }
        });
        btnAudioP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAudios(mediaPlayerPart2, tvTime2, btnAudioP2);
                resetAudios(mediaPlayerPart3, tvTime3, btnAudioP3);
                resetAudios(mediaPlayerPart1, tvTime4, btnAudioP1);
                try {
                    mediaPlayerPart4.setDataSource(ApiRequest.BASIC_URL + AssessorStartAct.storeresult.getPart_4());
                    mediaPlayerPart4.prepare();
                    durationsPart4 = mediaPlayerPart1.getDuration();
                    tvTime4.setText(milisecondToString(durationsPart4,TYPE.NUM));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(!mediaPlayerPart4.isPlaying()){
                    mediaPlayerPart4.start();
                    btnAudioP4.setVisibility(View.GONE);
                    tvTime4.setVisibility(View.VISIBLE);
                }else {
                    mediaPlayerPart4.pause();
                    tvTime4.setVisibility(View.GONE);
                    btnAudioP4.setVisibility(View.VISIBLE);
                }
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
        stopAudios(mediaPlayerPart1);
        stopAudios(mediaPlayerPart2);
        stopAudios(mediaPlayerPart3);
        stopAudios(mediaPlayerPart4);
        back();
    }
    private void dialogScore(final int pos){
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
                if (pos==1){
                    AssessorStartAct.storeresult.setPart_1_score(score);
                    scorePart1.setText(score);
                }else if (pos==2){
                    AssessorStartAct.storeresult.setPart_2_score(score);
                    scorePart2.setText(score);
                }else if (pos==3){
                    AssessorStartAct.storeresult.setPart_3_score(score);
                    scorePart3.setText(score);
                }else if (pos==4){
                    AssessorStartAct.storeresult.setPart_4_score(score);
                    scorePart4.setText(score);
                }
            }
        });
        dialog_font.setCancelable(true);
        dialog_font.show();
    }
    private void setProperties(View view){
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont((TextView) view.findViewById(R.id.lbexamId));
        FontUtils.setFont(tvTime1);
        FontUtils.setFont(tvTime2);
        FontUtils.setFont(tvTime3);
        FontUtils.setFont(tvTime4);
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_score1));
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_score2));
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_score3));
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_score4));
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView) view.findViewById(R.id.tvExamId));
        FontUtils.setFont((TextView) view.findViewById(R.id.lbTitle));
        FontUtils.setFont(tvSubmit);
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_header1));
        FontUtils.setFont((RelativeLayout) view.findViewById(R.id.rl_header2));
        FontUtils.setFont((LinearLayout) view.findViewById(R.id.ll_header3));
        FontUtils.setFont((LinearLayout) view.findViewById(R.id.ll_header4));
    }
    private void setExamResult() {
        tvExamId.setText(AssessorStartAct.storeresult.getExam_id());
        title_part1.setText("PART 1: " + AssessorStartAct.storeresult.getPart1());
        title_part2.setText("PART 2: " + AssessorStartAct.storeresult.getPart2_text());
        title_part3.setText(AssessorStartAct.storeresult.getPart3_text());
        title_part4.setText(AssessorStartAct.storeresult.getPart4_text());
        scorePart1.setText(String.valueOf(AssessorStartAct.storeresult.getPart_1_score()));
        scorePart2.setText(String.valueOf(AssessorStartAct.storeresult.getPart_2_score()));
        scorePart3.setText(String.valueOf(AssessorStartAct.storeresult.getPart_3_score()));
        scorePart4.setText(String.valueOf(AssessorStartAct.storeresult.getPart_4_score()));

        mediaPlayerPart1 = new MediaPlayer();
        mediaPlayerPart2 = new MediaPlayer();
        mediaPlayerPart3 = new MediaPlayer();
        mediaPlayerPart4 = new MediaPlayer();
        mediaPlayerPart1.setOnCompletionListener(this);
        mediaPlayerPart2.setOnCompletionListener(this);
        mediaPlayerPart3.setOnCompletionListener(this);
        mediaPlayerPart4.setOnCompletionListener(this);

    }
    private void resetAudios(MediaPlayer mp, TextView tvTime, ImageView ivPlay){
        if(mp.isPlaying()){
            mp.pause();
            tvTime.setVisibility(View.GONE);
            ivPlay.setVisibility(View.VISIBLE);
        }
    }
    private void stopAudios(MediaPlayer mp){
        if(mp.isPlaying()){
            mp.stop();
        }
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == mediaPlayerPart1){
            btnAudioP1.setImageResource(R.drawable.btn_play);
            btnAudioP1.setVisibility(View.VISIBLE);
            tvTime1.setVisibility(View.GONE);
        }else if (mp == mediaPlayerPart2){
            btnAudioP2.setImageResource(R.drawable.btn_play);
            btnAudioP2.setVisibility(View.VISIBLE);
            tvTime2.setVisibility(View.GONE);
        }else if (mp == mediaPlayerPart3){
            btnAudioP3.setImageResource(R.drawable.btn_play);
            btnAudioP3.setVisibility(View.VISIBLE);
            tvTime3.setVisibility(View.GONE);
        }else if (mp == mediaPlayerPart4){
            btnAudioP4.setImageResource(R.drawable.btn_play);
            btnAudioP4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.GONE);
        }
    }
    public String milisecondToString(long milliseconds, TYPE type) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (type == TYPE.NUM) {
            return (hours > 9 ? hours : ("0" + hours)) + ":"
                    + (minutes > 9 ? minutes : ("0" + minutes)) + ":"
                    + (seconds > 9 ? seconds : ("0" + seconds));
        } else {
            if (hours == 0 && minutes == 0)
                return seconds + "s";
            else if (hours == 0)
                return minutes + "m" + seconds + "s";
            else
                return hours + "h" + minutes + "m" + seconds + "s";
        }
    }
    public static enum TYPE {
        NUM, CHAR
    }

}
