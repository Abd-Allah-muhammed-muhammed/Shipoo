package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterAplayAcceptNoti;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.ServiceAccept;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notification2Activity extends AppCompatActivity {

    private List<ServiceAccept> listOfData = new ArrayList<>();
    private RecyclerView get_infoService_rv;
    private AdapterAplayAcceptNoti adapterAplayAcceptNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        get_infoService_rv = findViewById(R.id.notification2_rv);


      String  uid = SharedPreferencesManger.LoadStringData(this, "uid");

      getNotifications(uid);



    }

    private void getNotifications(String uid) {

        Query query = FirebaseDatabase.getInstance().getReference().child("service_aplay");

        query.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ServiceAccept value = snapshot.getValue(ServiceAccept.class);

                    listOfData.add(value);
                    get_infoService_rv.setLayoutManager(new LinearLayoutManager(Notification2Activity.this));
                    adapterAplayAcceptNoti =  new AdapterAplayAcceptNoti(Notification2Activity.this ,listOfData ,Notification2Activity.this);
                    get_infoService_rv.setAdapter(adapterAplayAcceptNoti);
                    adapterAplayAcceptNoti.notifyDataSetChanged();




                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
