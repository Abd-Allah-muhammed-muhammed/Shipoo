package com.example.mashaweer.ui.notifications;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterAcceptMandoop;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AcceptMandoop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationMandoopActivity extends AppCompatActivity {

    private List<AcceptMandoop> listOfData = new ArrayList<>();
    private RecyclerView get_infoService_rv;
    private AdapterAcceptMandoop adapterAcceptMandoop;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        get_infoService_rv = findViewById(R.id.notification2_rv);


         token = SharedPreferencesManger.LoadStringData(this, "token");
        getNotifications();



    }

    private void getNotifications() {

        // query by uid
Query query = FirebaseDatabase.getInstance().getReference().child("AcceptMandoop");

query.orderByChild("tokenMandoop").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AcceptMandoop value = snapshot.getValue(AcceptMandoop.class);

                    listOfData.add(value);
                    get_infoService_rv.setLayoutManager(new LinearLayoutManager(NotificationMandoopActivity.this));
                    adapterAcceptMandoop =  new AdapterAcceptMandoop(NotificationMandoopActivity.this ,listOfData , NotificationMandoopActivity.this );
                    get_infoService_rv.setAdapter(adapterAcceptMandoop);
                    adapterAcceptMandoop.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }







}
