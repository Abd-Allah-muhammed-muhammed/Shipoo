package com.example.mashaweer.ui.service;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddServiceActivity extends AppCompatActivity  {
    EditText cost ,price,location , type  ,locationDeleverd;
    Button puplish ;

    private DatabaseReference databaseReferance;
    private String token;
    private SweetAlertDialog pDialog;
    private boolean special;
    private String hours;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        puplish  = findViewById(R.id.add_service);
        type  = findViewById(R.id.type);
        location  = findViewById(R.id.location);
        price  =findViewById(R.id.price);
        cost = findViewById(R.id.cost);

         special = getIntent().getBooleanExtra("special", false);

        locationDeleverd = findViewById(R.id.location_dlever);
        token = SharedPreferencesManger.LoadStringData(this,"token");
        pDialog = new SweetAlertDialog(this);


         hours = new SimpleDateFormat("h:mm a dd MMMM yyyy", Locale.getDefault()).format(new Date());




        puplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puplishData(type,location,price,cost);
            }
        });

    }








    private void puplishData(EditText typeEt, EditText locationEt, EditText priceEt, EditText costEt) {

        // add dialog

        pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        String type = typeEt.getText().toString();
        String location = locationEt.getText().toString();
        String price = priceEt.getText().toString();
        String cost = costEt.getText().toString();
        String locationDeleverd_ = locationDeleverd.getText().toString();

        databaseReferance = FirebaseDatabase.getInstance().getReference().child("Service");

        String uniqueID = UUID.randomUUID().toString();
        Service serviceData = new Service( location, price, cost, type, token, uniqueID, locationDeleverd_ , special , hours );
       databaseReferance.push().setValue(serviceData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                StyleableToast.makeText(AddServiceActivity.this, "تم ارسال طلبك", Toast.LENGTH_LONG, R.style.mytoast).show();
                             finish();
                pDialog.cancel();


            }

        }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
               pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
               pDialog.setTitleText(""+e.getMessage());
               pDialog.setCancelable(true);
               pDialog.show();

           }
       });




    }
}
