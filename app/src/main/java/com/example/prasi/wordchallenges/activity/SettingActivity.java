package com.example.prasi.wordchallenges.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.example.prasi.wordchallenges.R;

public class SettingActivity extends LocalizationActivity {
    private Button btnChangeLanguage;
    private String language;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnChangeLanguage = findViewById(R.id.btnChangeLanguage);
        language = getString(R.string.language);
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equals("English")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "en",Toast.LENGTH_LONG).show();
                    setLanguage("th");
                }else {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "en",Toast.LENGTH_LONG).show();
                    setLanguage("en");
                }
            }
        });

    }
}
