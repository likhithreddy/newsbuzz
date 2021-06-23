package com.newsbuzz.activity;

import android.os.Bundle;

import com.newsbuzz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;


public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.fragment_logo);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}