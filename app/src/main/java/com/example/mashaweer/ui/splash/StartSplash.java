package com.example.mashaweer.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.mashaweer.R;

import com.example.mashaweer.ui.LoginActivity;
import com.google.firebase.iid.FirebaseInstanceId;
public class StartSplash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_splash);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                        Intent intent = new Intent(StartSplash.this, LoginActivity.class);
                        startActivity(intent);

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    }

