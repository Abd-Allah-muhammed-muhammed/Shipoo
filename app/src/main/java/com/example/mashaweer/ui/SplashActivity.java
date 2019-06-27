package com.example.mashaweer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

public class SplashActivity extends AppCompatActivity  {
Button addService , getService ;
    private Intent intent;
    private int User_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        addService = findViewById(R.id.add_service);
        getService =findViewById(R.id.get_service);




























        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();





        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtra("user_id",User_id);
                startActivity(intent);

                String token = FirebaseInstanceId.getInstance().getToken();
                Log.i("55", "onCreate: "+token);

            }
        });

        getService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
