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

import com.wodule.R;
import com.wodule.activities.StartAssessmentAct;
import com.wodule.commonhelps.LakConst;
import com.wodule.custom.BaseTFragment;
import com.wodule.custom.CustomProgress;
import com.wodule.utils.FontUtils;
import com.wodule.utils.UtilityRecord;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 5/9/2017.
 */

public class Part1 extends BaseTFragment implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ImageView ivNext;
    private TextView btnRecord;
    private TextView tvTime, tvContent;
    int w;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private boolean record = true;
    private ProgressBar record_progress_bar;
    private ProgressBar record_progress_bar2;
    private CustomProgress pb;
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
        tvTime.setText(milisecondToString(duration, TYPE.NUM));
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
                        audiofile1 = new File(Environment.getExternalStorageDirectory() + "/"
//                                + format.format(Calendar.getInstance().getTime())
                                + "_audioPart1.m4a");
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
            tvTime.setText(milisecondToString(count, TYPE.NUM));
            mhHandler.postDelayed(mRunnable, 1000);
            Log.e("RecordActivity", "" + count);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1, container, false);
        ivNext = (ImageView) view.findViewById(R.id.ivNext);
        btnRecord = (TextView) view.findViewById(R.id.btnRecord);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvContent = (TextView) view.findViewById(R.id.tvContent);

        record_progress_bar = (ProgressBar) view.findViewById(R.id.record_progress_bar);
        record_progress_bar2 = (ProgressBar) view.findViewById(R.id.progressBar1);
        tvContent.setText(String.valueOf(StartAssessmentAct.examObj.getPart1()));
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewScreen(Part1.this, new Part2());
            }
        });
        btnRecord.setOnClickListener(this);
        new DelayTask().execute();

        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_REGULAR);
        FontUtils.setFont(btnRecord);
        FontUtils.setFont(tvTime);
        FontUtils.setFont(tvContent);
        FontUtils.loadFont(getActivity(), LakConst.FONT_HEV_MEDIUM);
        FontUtils.setFont((TextView)view.findViewById(R.id.lbHeader));
        FontUtils.setFont((TextView)view.findViewById(R.id.lbTitle));

        return view;
    }

    public class DelayTask extends AsyncTask<Void, Integer, String> {
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
                            audiofile1 = new File(Environment.getExternalStorageDirectory() + "/"
//                                    + format.format(Calendar.getInstance().getTime())
                                    + "_audioPart1.m4a");
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
            tvTime.setText(milisecondToString(millisUntilFinished, TYPE.NUM));
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(audiofile1.getAbsolutePath());
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
        audiofile1 = null;
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
                    tvTime.setText(milisecondToString(duration, TYPE.NUM));
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
