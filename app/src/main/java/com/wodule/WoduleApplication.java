package com.wodule;

import android.app.Application;

import com.steelkiwi.instagramhelper.InstagramHelper;

/**
 * Created by MyPC on 16/06/2017.
 */
public class WoduleApplication extends Application {
    public static final String CLIENT_ID     = "83517cb5034047f2be190f79e6fc8fda";
    public static final String REDIRECT_URL = "http://wodule.io";

    private static InstagramHelper instagramHelper;
    String scope = "basic";
    @Override
    public void onCreate() {
        super.onCreate();
        instagramHelper = new InstagramHelper.Builder()
                .withClientId(CLIENT_ID)
                .withRedirectUrl(REDIRECT_URL)
                .withScope(scope)
                .build();
    }

    public static InstagramHelper getInstagramHelper() {
        return instagramHelper;
    }
}