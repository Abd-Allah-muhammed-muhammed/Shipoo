package com.example.mashaweer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AcceptMandoop;
import com.example.mashaweer.model.AcceptService;
import com.example.mashaweer.model.Users;
import com.example.mashaweer.ui.SowInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;


public class AdapterAcceptService extends RecyclerView.Adapter<AdapterAcceptService.ViewHolder> {

    Context context;
    List<AcceptService> listOfnotifications = new ArrayList<>();
    Activity activity ;
    private DatabaseReference databaseReferance;



    // private ApiServices apiServices;


    public AdapterAcceptService(Context context, List<AcceptService> listOfnotifications , Activity activity) {
        this.context = context;
        this.listOfnotifications = listOfnotifications;
        this.activity= activity;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notifications, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final AcceptService data = listOfnotifications.get(i);
        String type = data.getType();
        viewHolder.titel.setText( " طلب بخصوص : "+type);
      String   idItem = data.getIdItem();
    String    token = data.getToken();
         String tokenUser = data.getTokenUser();
       String mToken = SharedPreferencesManger.LoadStringData(activity,"token");


 viewHolder.dont_agree.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

         listOfnotifications.remove(i);
         deletDataFromeAccept(idItem);

     }
 });



        viewHolder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pushnotification();
                databaseReferance = FirebaseDatabase.getInstance().getReference().child("AcceptMandoop");
                AcceptMandoop serviceAccept = new AcceptMandoop(idItem,mToken ,token);
                databaseReferance.push().setValue(serviceAccept);



viewHolder.layout_noti_.setVisibility(View.GONE);
                viewHolder.show_Info_.setVisibility(View.VISIBLE);
                viewHolder.show_Info_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // mandoop
                        final Query query = FirebaseDatabase.getInstance().getReference().child("Users");
                        query.orderByChild("token").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
                            @java.lang.Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    Users clint = snapshot.getValue(Users.class);

                                    String name = clint.getName();
                                    String address = clint.getAddress();
                                    String mail = clint.getMail();
                                    String phone = clint.getPhone();
                                    String token = clint.getToken();


                                    Intent intent = new Intent(context, SowInfoActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("address", address);
                                    intent.putExtra("mail", mail);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("token", token);
                                    intent.putExtra("item_id", idItem);

                                    listOfnotifications.remove(i);
                                    deletDataFromeAccept(idItem);

                                    activity.startActivity(intent);




                                }


                            }



                            @java.lang.Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(context, "error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });


                    }
                });





            }
        });


    }

    private void deletDataFromeAccept(String idItem) {

        Query query = FirebaseDatabase.getInstance().getReference().child("AcceptService");
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


    private void pushtDeal() {





    }

    private void pushnotification() {


    }
    public void refreshEvents(List<AcceptService> events) {
        this.listOfnotifications.clear();
        this.listOfnotifications.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfnotifications.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titel;
        public Button agree ,  show_Info_ ,   dont_agree ;
        public LinearLayout layout_noti_;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel =  itemView.findViewById(R.id.titel_noti);
            agree =  itemView.findViewById(R.id.agree);
            show_Info_ = itemView.findViewById(R.id.show_Info_);
            layout_noti_= itemView.findViewById(R.id.layout_noti_);
            dont_agree = itemView.findViewById(R.id.dont_agree );


        }
    }
}