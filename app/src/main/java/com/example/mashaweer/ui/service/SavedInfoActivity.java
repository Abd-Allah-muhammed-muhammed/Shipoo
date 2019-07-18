package com.example.mashaweer.ui.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterSavedInfo;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.SaveInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedInfoActivity extends AppCompatActivity {

    @BindView(R.id.rv_add_info_saved)
    RecyclerView rvAddInfoSaved;
    AdapterSavedInfo adapterSavedInfo ;
    List<SaveInfo> saveInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_info);
        ButterKnife.bind(this);


       int id = SharedPreferencesManger.LoadIntegerData(this, "id");
        String token = SharedPreferencesManger.LoadStringData(this, "token");


        rvAddInfoSaved.setLayoutManager(new LinearLayoutManager(this));
        adapterSavedInfo = new AdapterSavedInfo(this,saveInfoList , id);


        Query query = FirebaseDatabase.getInstance().getReference().child("SaveInfo");
        query.orderByChild("token").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                     ) {


                    SaveInfo value = snapshot.getValue(SaveInfo.class);

                    saveInfoList.add(value);
                    rvAddInfoSaved.setAdapter(adapterSavedInfo);
        adapterSavedInfo.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
