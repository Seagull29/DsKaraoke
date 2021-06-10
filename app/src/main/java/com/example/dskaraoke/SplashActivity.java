package com.example.dskaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startActivity(new Intent(this, MainActivity.class));

        TimerTask time = new TimerTask() {
            @Override
            public void run() {
                Intent inten = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(inten);
                finish();
            }
        };
        // Se ejecuta una vez cerrado
        Timer timer = new Timer();
        timer.schedule(time, SPLASH_SCREEN_DELAY);

    }
}