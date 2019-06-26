package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.Collator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.model.Singup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SowInfoActivity extends AppCompatActivity {

    TextView nameTv, addressTv, phoneTv, mailTv;
    private String phone;
    RatingBar rating_bar_add;
    private String uid;
    private DatabaseReference databaseRefrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sow_info);
        nameTv = findViewById(R.id.show_Name);
        addressTv = findViewById(R.id.show_address);
        phoneTv = findViewById(R.id.show_Phone);
        mailTv = findViewById(R.id.show_Email);
        rating_bar_add = findViewById(R.id.rating_bar_add);


        rating_bar_add.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

               float  rating = ratingBar.getRating();


                 sendRating(rating);


            }
        });



        getIntentData();

        phoneTv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @java.lang.Override
            public void onClick(View view) {

                onCallBtnClick();
            }
        });
    }

    private void sendRating(float rating) {

        databaseRefrance = FirebaseDatabase.getInstance().getReference().child("users");
        databaseRefrance.orderByChild("uid").equalTo(uid);
        Singup singup = new Singup();
        singup.setRotate(rating);
        databaseRefrance.setValue(singup);
    }

    private void getIntentData() {

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        String mail = intent.getStringExtra("mail");
         uid = intent.getStringExtra("uid");

        mailTv.setText(mail);
        nameTv.setText(name);
        addressTv.setText(address);
        phoneTv.setText(phone);
    }


    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
