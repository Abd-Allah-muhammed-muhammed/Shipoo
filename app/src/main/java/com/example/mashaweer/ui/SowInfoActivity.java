package com.example.mashaweer.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.SaveInfo;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.ui.home.HomeActivity;
import com.example.mashaweer.ui.service.SavedInfoActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SowInfoActivity extends AppCompatActivity {

    @BindView(R.id.show_name)
    TextView showName;
    @BindView(R.id.show_address)
    TextView showAddress;
    @BindView(R.id.show_Phone)
    TextView showPhone;
    @BindView(R.id.show_mail)
    TextView showMail;
    @BindView(R.id.show_type)
    TextView showType;
    @BindView(R.id.show_location_delivery)
    TextView showLocationDelivery;
    @BindView(R.id.show_price)
    TextView showPrice;
    @BindView(R.id.show_location)
    TextView showLocation;
    @BindView(R.id.show_cost)
    TextView showCost;
    @BindView(R.id.show_save)
    Button showSave;
    private String phone;
    private DatabaseReference databaseRefrance;
    private String token;
    private String item_id;
    private String type_servis, mail, address, location_service, location_delevry, price_service, cost_service, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sow_info);
        ButterKnife.bind(this);


        getIntentData();

        getInfoService();
        showPhone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                onCallBtnClick();
            }
        });
    }

    private void getInfoService() {
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Service");
        query2.orderByChild("id").equalTo(item_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {
                    Service value = snapshot.getValue(Service.class);
                    type_servis = value.getType();

                    cost_service = value.getCost();
                    location_service = value.getFrom();
                    price_service = value.getPrice();
                    location_delevry = value.getTo();

                    showCost.setText(cost_service);
                    showType.setText(type_servis);
                    showLocation.setText(location_service);
                    showLocationDelivery.setText(location_delevry);
                    showPrice.setText(price_service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    private void sendRating(float rating) {
//
//        databaseRefrance = FirebaseDatabase.getInstance().getReference().child("Users");
//        databaseRefrance.orderByChild("token").equalTo(token);
//        Users users = new Users();
//        users.setRotate(rating);
//        databaseRefrance.setValue(users);
//    }

    private void getIntentData() {

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        mail = intent.getStringExtra("mail");
        item_id = intent.getStringExtra("item_id");


        token = SharedPreferencesManger.LoadStringData(this, "token");
        showMail.setText(mail);
        showName.setText(name);
        showAddress.setText(address);
        showPhone.setText(phone);
    }


    private void onCallBtnClick() {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        } else {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


    private void saveinfo() {


        String id = UUID.randomUUID().toString();

        DatabaseReference saveInfoFirebase = FirebaseDatabase.getInstance().getReference().child("SaveInfo");
        SaveInfo saveInfo = new SaveInfo(id, name, address, phone, mail, type_servis, location_service, price_service, location_delevry, cost_service, item_id, token);

        saveInfoFirebase.push().setValue(saveInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(SowInfoActivity.this, "تم حفظ البيانات بنجاح", Toast.LENGTH_SHORT).show();
                showSave.setClickable(false);
               Intent intent = new Intent(SowInfoActivity.this, HomeActivity.class);
               startActivity(intent);
            }
        });

    }

    @OnClick(R.id.show_save)
    public void onViewClicked() {

        saveinfo();
    }
}
