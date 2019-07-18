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
import com.example.mashaweer.ui.home.SecondHomeActivity;
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

public class SowInfoActivity2 extends AppCompatActivity {
    @BindView(R.id.show_name2)
    TextView showName2;
    @BindView(R.id.show_address2)
    TextView showAddress2;
    @BindView(R.id.show_Phone2)
    TextView showPhone2;
    @BindView(R.id.show_mail2)
    TextView showMail2;
    @BindView(R.id.show_type2)
    TextView showType2;
    @BindView(R.id.show_location_delivery2)
    TextView showLocationDelivery2;
    @BindView(R.id.show_price2)
    TextView showPrice2;
    @BindView(R.id.show_location2)
    TextView showLocation2;
    @BindView(R.id.show_cost2)
    TextView showCost2;
    @BindView(R.id.show_save2)
    Button showSave2;
    Button showFinish2;
    private String phone;
    private String id_item;
    private String type_servis, mail, address, location_service, location_delevry, price_service, cost_service, name , token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sow_info2);
        ButterKnife.bind(this);





        getIntentData();
        getInfoService();


        showPhone2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                onCallBtnClick();

            }
        });

    }


    private void getInfoService() {
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Service");
        query2.orderByChild("id").equalTo(id_item).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {
                    Service value = snapshot.getValue(Service.class);
                     type_servis = value.getType();

                     cost_service = value.getCost();
                     location_service = value.getFrom();
                     price_service = value.getPrice();
                     location_delevry = value.getTo();

                    showCost2.setText(cost_service);
                    showType2.setText(type_servis);
                    showLocation2.setText(location_service);
                    showLocationDelivery2.setText(location_delevry);
                    showPrice2.setText(price_service);
                }
            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getIntentData() {
        token = SharedPreferencesManger.LoadStringData(this, "token");

        Intent intent = getIntent();
         name = intent.getStringExtra("name");
         address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
         mail = intent.getStringExtra("mail");
         id_item = intent.getStringExtra("id_item");

        showMail2.setText(mail);
        showName2.setText(name);
        showAddress2.setText(address);
        showPhone2.setText(phone);
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
        finish();
    }

    @OnClick(R.id.show_save2)
    public void onViewClicked() {

saveInfo();

    }

    private void saveInfo() {


        String id = UUID.randomUUID().toString();

        DatabaseReference saveInfoFirebase = FirebaseDatabase.getInstance().getReference().child("SaveInfo");
        SaveInfo saveInfo = new SaveInfo(id, name, address, phone, mail, type_servis, location_service, price_service, location_delevry, cost_service, id_item, token);

        saveInfoFirebase.push().setValue(saveInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(SowInfoActivity2.this, "تم حفظ البيانات بنجاح", Toast.LENGTH_SHORT).show();
                showSave2.setClickable(false);
                Intent intent = new Intent(SowInfoActivity2.this, SecondHomeActivity.class);
                startActivity(intent);
            }
        });
    }
    }





