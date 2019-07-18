package com.example.mashaweer.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterAcceptService;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AcceptService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationUserActivity extends AppCompatActivity {

    private DatabaseReference databaseReferance;
    private List<AcceptService> listofDataService = new ArrayList<>();
    private RecyclerView get_infoService_rv;
    private AdapterAcceptService adapterAcceptService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        get_infoService_rv = findViewById(R.id.notification_rv);


String mToken = SharedPreferencesManger.LoadStringData(this, "token");

getnotifications(mToken);



    }

    private void getnotifications(final String mToken) {


        Query query = FirebaseDatabase.getInstance().getReference().child("AcceptService");

        query.orderByChild("tokenUser").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AcceptService value = snapshot.getValue(AcceptService.class);
                    String token = value.getTokenUser();

                    if (token.equals(mToken)){


                        listofDataService.add(value);
                        get_infoService_rv.setLayoutManager(new LinearLayoutManager(NotificationUserActivity.this));
                        adapterAcceptService = new AdapterAcceptService(NotificationUserActivity.this ,listofDataService , NotificationUserActivity.this);
                        get_infoService_rv.setAdapter(adapterAcceptService);
                        adapterAcceptService.notifyDataSetChanged();




                    }else {
                        Toast.makeText(NotificationUserActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
