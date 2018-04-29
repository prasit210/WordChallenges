package com.example.prasi.wordchallenges.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;

import com.example.prasi.wordchallenges.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity{
    private SharedPreferences sp;
   Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            if (sp.getBoolean("Status",false) == true){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },2000);
            }
}
}
