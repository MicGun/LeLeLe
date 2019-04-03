package com.hugh.lelele;

import android.app.Application;
import android.content.Context;

public class LeLeLe extends Application {

    private static Context mContext;

    public LeLeLe() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getAppContext();
    }

    public static Context getAppContext() {
        return mContext;
    }


}
