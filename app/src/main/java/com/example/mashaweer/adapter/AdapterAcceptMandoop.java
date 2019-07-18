package com.example.mashaweer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.model.AcceptMandoop;

import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.Users;
import com.example.mashaweer.ui.SowInfoActivity2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterAcceptMandoop extends RecyclerView.Adapter<AdapterAcceptMandoop.ViewHolder> {

    Context context;
    List<AcceptMandoop> listOfnotifications = new ArrayList<>();
    Activity activity;
    private DatabaseReference databaseReferance;


    // private ApiServices apiServices;


    public AdapterAcceptMandoop(Context context, List<AcceptMandoop> listOfnotifications, Activity activity) {
        this.context = context;
        this.listOfnotifications = listOfnotifications;
        this.activity = activity;


    }

//    public void refreshEvents(List<AcceptMandoop> events) {
//        this.listOfnotifications.clear();
//        this.listOfnotifications.addAll(events);
//        notifyDataSetChanged();
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.aply_is_ok, viewGroup, false);
        return new AdapterAcceptMandoop.ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final AcceptMandoop data = listOfnotifications.get(i);

        String   idItem = data.getIdItem();

        Query query2 = FirebaseDatabase.getInstance().getReference().child("Service");
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


        viewHolder.showInfo.setOnClickListener(new View.OnClickListener() {
            @java.lang.Override
            public void onClick(View view) {

                // user
                String tokenUser = data.getTokenUser();
                Query query = FirebaseDatabase.getInstance().getReference().child("Users");

                query.orderByChild("token").equalTo(tokenUser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @java.lang.Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()
                        ) {


                            Users value = snapshot.getValue(Users.class);
                            String mail = value.getMail();
                            String name = value.getName();
                            String address = value.getAddress();
                            String phone = value.getPhone();
                            String token = value.getToken();
                            Intent intent = new Intent(context, SowInfoActivity2.class);
                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("mail", mail);
                            intent.putExtra("phone", phone);
                            intent.putExtra("token", token);
                            intent.putExtra("id_item", idItem);

                            activity.startActivity(intent);

                        }
                    }

                    @java.lang.Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        viewHolder.delet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletItem(i,idItem);

            }
        });

      //


    }

    private void deletItem(int i , String idItem) {

        listOfnotifications.remove(i);



        Query query = FirebaseDatabase.getInstance().getReference().child("AcceptMandoop");
        query.orderByChild("idItem").equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    String key = appleSnapshot.getKey();
                    dataSnapshot.child(key).getRef().removeValue();

                    notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return listOfnotifications.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titel,showInfo;
        public Button delet_btn ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel = itemView.findViewById(R.id.agree_);
            showInfo = itemView.findViewById(R.id.Show_info_user);
            delet_btn = itemView.findViewById(R.id.noti_item_delete);


        }
    }


}

