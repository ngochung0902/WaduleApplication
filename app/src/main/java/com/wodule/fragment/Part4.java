package com.wodule.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wodule.R;
import com.wodule.activities.StartAssessmentAct;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;
import com.wodule.custom.BaseTFragment;
import com.wodule.custom.CustomProgress;
import com.wodule.object.ResponResult;
import com.wodule.object.UserObject;
import com.wodule.utils.ApiRequest;
import com.wodule.utils.FontUtils;
import com.wodule.utils.UtilityRecord;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 5/9/2017.
 */

public class Part4 extends BaseTFragment implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ImageView ivBook;
    private ImageView ivNext;
    private TextView btnRecord;
    private TextView tvTime, lbQuestion;
    int w;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private boolean record = true;
    private ProgressBar record_progress_bar;
    private ProgressBar record_progress_bar2;
    long count = 0;
    long duration = 0;
    int isPlay = 0;
    private String userChoosenTask;
    MyCountDown timerCount;

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        duration = mediaPlayer.getDuration();
        timerCount = new MyCountDown(mediaPlayer.getDuration(), 1000);
        timerCount.start();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        tvTime.setText(milisecondToString(duration, Part1.TYPE.NUM));
    }

    @Override
    public void onClick(View v) {
        if (v == btnRecord) {
            tvTime.setVisibility(View.VISIBLE);
            btnRecord.setVisibility(View.GONE);
            count = 0;
            if (record) {
                boolean result = UtilityRecord.checkPermission(getActivity());
                userChoosenTask = "Record Audio";
                if (result) {
                    try {
//                        SimpleDateFormat format = new SimpleDateFormat("EEEddMMyyyy_HHmmss");
                        audiofile4 = new File(Environment.getExternalStorageDirectory() + "/"
//                                + format.format(Calendar.getInstance().getTime())
                                + "_audioPart4.m4a");
                        new DelayTask2().execute();
                        startRecording();
                        mhHandler.post(mRunnable);
                    } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(),"Please grant camera permission for the app in Settings",Toast.LENGTH_LONG).show();
                        Log.e("MainActivity", "" + e.toString());
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                2);
                    }
                }
            } else {
                record = true;
                stopRecording();
                isPlay = 2;
                mhHandler.removeCallbacks(mRunnable);
            }
        }
    }

    public static enum TYPE {
        NUM, CHAR
    }

    private Handler mhHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        public void run() {
            count += 1000;
            tvTime.setText(milisecondToString(count, Part1.TYPE.NUM));
            mhHandler.postDelayed(mRunnable, 1000);
            if (count >= 11000) {
                count = 0;
                record = true;
                stopRecording();
                isPlay = 2;
                mhHandler.removeCallbacks(mRunnable);
                ivNext.setVisibility(View.VISIBLE);
            }

        }
    };

    public String milisecondToString(long milliseconds, Part1.TYPE type) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (type == Part1.TYPE.NUM) {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part4, container, false);
        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        ivBook = (ImageView) view.findViewById(R.id.ivBook);
        record_progress_bar = (ProgressBar) view.findViewById(R.id.record_progress_bar);
        btnRecord = (TextView) view.findViewById(R.id.btnRecord);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        record_progress_bar2 = (ProgressBar) view.findViewById(R.id.progressBar1);
        lbQuestion = (TextView) view.findViewById(R.id.lbQuestion);
        lbQuestion.setText(String.valueOf(StartAssessmentAct.examObj.getPart4_text()));
        Glide.with(getActivity()).load(String.valueOf(StartAssessmentAct.examObj.getPart4_url()))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBook);
        LakRun.setLayoutView(ivBook, LakRun.GetWidthDevice(getActivity()) / 2, LakRun.GetWidthDevice(getActivity()) / 2);
        btnRecord.setOnClickListener(this);
        new DelayTask().execute();
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audiofile1 != null) {
                    pDialog.setMessage("Uploading...");
                    pDialog.show();
                    Ion.with(getActivity())
                            .load("POST",ApiRequest.URL_STORE_RESULT)
                            .setHeader("Authorization", "Bearer " + LakRun.getAccessToken(getActivity()))
                            .setMultipartFile("part_1", new File(audiofile1.getPath()))
                            .setMultipartFile("part_2", new File(audiofile2.getPath()))
                            .setMultipartFile("part_3", new File(audiofile3.getPath()))
                            .setMultipartFile("part_4", new File(audiofile4.getPath()))
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    pDialog.dismiss();
                                    if (e == null){
                                        Log.e("ExamActivity", "result:" +result);
//                                        Gson gson = new Gson();
//                                        ResponResult storeresult = gson.fromJson(result,ResponResult.class);
                                        startNewScreen(Part4.this, new PartEnd());
                                    }
                                }
                            });
                }

            }
        });

        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont(btnRecord);
        FontUtils.setFont(tvTime);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbQuestion));
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbHeader));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));
        return view;
    }

    class DelayTask extends AsyncTask<Void, Integer, String> {
        int count = 0;

        @Override
        protected void onPreExecute() {
            record_progress_bar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            while (count < 100) {
                SystemClock.sleep(50);
                count++;
                publishProgress(count * 1);
            }

            return "Complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            record_progress_bar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            btnRecord.setVisibility(View.VISIBLE);
        }
    }

    class DelayTask2 extends AsyncTask<Void, Integer, String> {
        int count = 0;

        @Override
        protected void onPreExecute() {
            record_progress_bar2.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            while (count < 100) {
                SystemClock.sleep(100);
                count++;
                publishProgress(count * 1 / 1);
            }

            return "Complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            record_progress_bar2.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case UtilityRecord.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_REC:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Record Audio")) {
                        try {
//                            SimpleDateFormat format = new SimpleDateFormat("EEEddMMyyyy_HHmmss");
                            audiofile4 = new File(Environment.getExternalStorageDirectory() + "/"
//                                    + format.format(Calendar.getInstance().getTime())
                                    + "_audioPart4.m4a");
                            startRecording();
                            mhHandler.post(mRunnable);
                        } catch (Exception e) {
//                                Toast.makeText(getApplicationContext(),"Please grant camera permission for the app in Settings",Toast.LENGTH_LONG).show();
                            Log.e("MainActivity", "" + e.toString());
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    2);
                        }
                    }
                } else {
                    //Toast.makeText(getApplicationContext(),"Please grant camera permission for the app in Settings",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public class MyCountDown extends CountDownTimer {
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
//            tvTime.setText(milisecondToString(duration, TYPE.NUM));
//            if (mPlayer.isPlaying()){
//                mPlayer.stop();
//                mPlayer.release();
//                mPlayer = null;
//                btnPlay.setImageResource(R.drawable.btn_play);
//            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvTime.setText(milisecondToString(millisUntilFinished, Part1.TYPE.NUM));
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(audiofile4.getAbsolutePath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            record = false;
            isPlay = 1;
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e("DictationAct", "prepare() failed");
            record = true;
            isPlay = 2;
            mhHandler.removeCallbacks(mRunnable);
        }
    }

    private void stopRecording() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {
        audiofile4 = null;
        if (isPlay == 1) {
            count = 0;
            record = true;
            stopRecording();
            isPlay = 2;
            mhHandler.removeCallbacks(mRunnable);
        } else if (isPlay == 2) {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                    timerCount.cancel();
                    tvTime.setText(milisecondToString(duration, Part1.TYPE.NUM));
                }
            }
            back();
        } else {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                }
            }
            back();
        }
    }
}