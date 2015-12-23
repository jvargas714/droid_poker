package com.droidpoker.jay.droidpoker;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;


public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIMER = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent enterAppIntent = new Intent(SplashActivity.this, EnterAppActivity.class);
                startActivity(enterAppIntent);
                finish();
            }
        }, SPLASH_DISPLAY_TIMER);
    }
}
