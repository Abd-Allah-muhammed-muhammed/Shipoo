package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterGetService;
import com.example.mashaweer.adapter.AdapterNotificatios;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Notificatios;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.ServiceAccept;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.mashaweer.adapter.AdapterNotificatios.idItem;

public class NotificationActivity extends AppCompatActivity {

    private DatabaseReference databaseReferance;
    private List<ServiceAccept> listofDataService = new ArrayList<>();
    private RecyclerView get_infoService_rv;
    private AdapterNotificatios adapterNotificatios;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        get_infoService_rv = findViewById(R.id.notification_rv);

         uid = SharedPreferencesManger.LoadStringData(this, "uid");

        databaseReferance = FirebaseDatabase.getInstance().getReference().child("service_accept");

        databaseReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ServiceAccept value = snapshot.getValue(ServiceAccept.class);
                    String uidV = value.getUid();

                    if (uidV.equals(uid)){

                        if (idItem.equals(value.getIdItem())){

                            // the value is already in the notification


                        }else {
                            listofDataService.add(value);
                            get_infoService_rv.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                            adapterNotificatios = new AdapterNotificatios(NotificationActivity.this ,listofDataService );
                            get_infoService_rv.setAdapter(adapterNotificatios);
                            adapterNotificatios.notifyDataSetChanged();
                        }


                    }else {
                        Toast.makeText(NotificationActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
