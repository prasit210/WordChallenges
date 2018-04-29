package com.example.prasi.wordchallenges;

import android.app.Application;

import com.example.prasi.wordchallenges.utils.Contextor;


public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
