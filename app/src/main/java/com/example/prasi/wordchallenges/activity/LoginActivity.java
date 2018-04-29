package com.example.prasi.wordchallenges.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.fragment.LoginFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
        }
        //checkFirstRun();

    }

    private void checkFirstRun() {
        sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        //Toast.makeText(getApplicationContext(),sp.getString("Name",""),Toast.LENGTH_LONG).show();

        if (sp.getBoolean("Status",false) == true){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
