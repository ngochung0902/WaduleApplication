package com.wodule.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.wodule.R;
import com.wodule.commonhelps.LakRun;

public class SplashAct extends Activity {
	protected int _splashTime = 1500;
	private ImageView iv_logo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		getWidthHeight();
		iv_logo = (ImageView) findViewById(R.id.iv_Logo);
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						// Wait given period of time or exit on touch

						wait(_splashTime);
						if (LakRun.getIsLogin(getApplicationContext())) {
							if (LakRun.getIsStudent(getApplicationContext())){
								Intent intent = new Intent(SplashAct.this,
										MainAssessmentActivity.class);
								startActivity(intent);
								finish();
							}else {
								Intent intent = new Intent(SplashAct.this,
										AssessorActivity.class);
								startActivity(intent);
								finish();
							}
						}else {
							Intent intent = new Intent(SplashAct.this,
									LoginActivity.class);
							startActivity(intent);
							finish();
						}

					}
				} catch (InterruptedException ex) {
				}

			}

		};
		splashTread.start();

	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void getWidthHeight() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int wwidth = displaymetrics.widthPixels;
		LakRun.SetWidthDevice(this, wwidth);
		LakRun.SetHeightDevice(this, height);
	}

}
