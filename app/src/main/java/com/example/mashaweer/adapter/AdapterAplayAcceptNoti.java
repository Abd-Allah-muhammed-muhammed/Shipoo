package com.example.mashaweer.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AplayAcscept;

import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.ServiceAccept;
import com.example.mashaweer.model.Singup;
import com.example.mashaweer.ui.SowInfoActivity;
import com.example.mashaweer.ui.SowInfoActivity2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterAplayAcceptNoti extends RecyclerView.Adapter<AdapterAplayAcceptNoti.ViewHolder> {

    Context context;
    List<AplayAcscept> listOfnotifications = new ArrayList<>();
    Activity activity;
    private DatabaseReference databaseReferance;


    // private ApiServices apiServices;


    public AdapterAplayAcceptNoti(Context context, List<AplayAcscept> listOfnotifications, Activity activity) {
        this.context = context;
        this.listOfnotifications = listOfnotifications;
        this.activity = activity;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.aply_is_ok, viewGroup, false);
        return new AdapterAplayAcceptNoti.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final AplayAcscept data = listOfnotifications.get(i);



        String idItem = data.getIdItem();
        Query query2 = FirebaseDatabase.getInstance().getReference().child("service");
        query2.orderByChild("id").equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {
                    Service value = snapshot.getValue(Service.class);
                    String type = value.getType();
                    viewHolder.titel.setText("تم قبول طلب :"+type);
                }
            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewHolder.titel.setOnClickListener(new View.OnClickListener() {
            @java.lang.Override
            public void onClick(View view) {

                String uid = data.getUidAccept();
                Query query = FirebaseDatabase.getInstance().getReference().child("users");

                query.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @java.lang.Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()
                        ) {


                            Singup value = snapshot.getValue(Singup.class);
                            String mail = value.getMail();
                            String name = value.getName();
                            String address = value.getAddress();
                            String phone = value.getPhone();
                            String mUid = value.getUid();


                            Intent intent = new Intent(context, SowInfoActivity2.class);
                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("mail", mail);
                            intent.putExtra("phone", phone);
                            intent.putExtra("uid", mUid);

                            activity.startActivity(intent);

                        }
                    }

                    @java.lang.Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return listOfnotifications.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.agree_);


        }
    }


}

