package com.example.mashaweer.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.mashaweer.model.SaveInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSavedInfo extends RecyclerView.Adapter<AdapterSavedInfo.ViewHolder> {

    Activity  activity;
    List<SaveInfo> lisInfo;

    private DatabaseReference databaseReferance;


    boolean cleiked  = false;
    int id ;
    // private ApiServices apiServices;


    public AdapterSavedInfo(Activity activity, List<SaveInfo> lisInfo, int id) {
        this.activity = activity;
        this.lisInfo = lisInfo;
        this.id = id;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_saved_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        final SaveInfo data = lisInfo.get(i);

        holder.saveName.setText(data.getName());
        holder.savePhone.setText(data.getPhone());
        holder.saveType.setText(data.getType_service());
        holder.saveLocationDelivery.setText(data.getLocation_delevry());

        holder.saveShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cleiked==false){
                    holder.layoutAddress.setVisibility(View.VISIBLE);
                    holder.layoutCost.setVisibility(View.VISIBLE);
                    holder.layoutLocation.setVisibility(View.VISIBLE);
                    holder.layoutMail.setVisibility(View.VISIBLE);
                    holder.layoutPrice.setVisibility(View.VISIBLE);

                    holder.saveAddress.setText(data.getAddress());
                    holder.saveCost.setText(data.getCost_service());
                    holder.saveLocation.setText(data.getLocation_service());
                    holder.saveMail.setText(data.getMail());
                    holder.savePrice.setText(data.getPreice_service());
                    holder.saveShowMore.setText("معلومات اقل");
                    holder.saveFinsh.setVisibility(View.VISIBLE);
                    cleiked=true;

                }else {

                    holder.layoutAddress.setVisibility(View.GONE);
                    holder.layoutCost.setVisibility(View.GONE);
                    holder.layoutLocation.setVisibility(View.GONE);
                    holder.layoutMail.setVisibility(View.GONE);
                    holder.layoutPrice.setVisibility(View.GONE);
                    holder.saveFinsh.setVisibility(View.GONE);

                    holder.saveShowMore.setText(" اظهر المزيد ");
                    cleiked = false;
                }




            }
        });

        holder.saveFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id==1){

                    deletService(data.getId_servis());
                    deleteInfo(data.getToken());
                }else {


                    deleteInfo(data.getToken());
                }

                deleteInfo(data.getToken());
            }
        });


    }

    private void deleteInfo(String token) {
        Query query = FirebaseDatabase.getInstance().getReference().child("SaveInfo");
        query.orderByChild("token").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    String key = appleSnapshot.getKey();
                    dataSnapshot.child(key).getRef().removeValue();
                    notifyDataSetChanged();

                    Toast.makeText(activity, "شكرا لتعاملكم معنا ", Toast.LENGTH_SHORT).show();
                    activity.finish();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void deletService(String idService) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Service");
        query.orderByChild("id").equalTo(idService).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    String key = appleSnapshot.getKey();
                    dataSnapshot.child(key).getRef().removeValue();
                    notifyDataSetChanged();

                    Toast.makeText(activity, "شكرا لتعاملكم معنا ", Toast.LENGTH_SHORT).show();
                    activity.finish();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return lisInfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.save_name)
        TextView saveName;
        @BindView(R.id.layout_name)
        LinearLayout layoutName;
        @BindView(R.id.save_address)
        TextView saveAddress;
        @BindView(R.id.layout_address)
        LinearLayout layoutAddress;
        @BindView(R.id.save_Phone)
        TextView savePhone;
        @BindView(R.id.layout_phone)
        LinearLayout layoutPhone;
        @BindView(R.id.save_mail)
        TextView saveMail;
        @BindView(R.id.layout_mail)
        LinearLayout layoutMail;
        @BindView(R.id.save_type)
        TextView saveType;
        @BindView(R.id.layout_type)
        LinearLayout layoutType;
        @BindView(R.id.save_location_delivery)
        TextView saveLocationDelivery;
        @BindView(R.id.layout_location_deleverd)
        LinearLayout layoutLocationDeleverd;
        @BindView(R.id.save_price)
        TextView savePrice;
        @BindView(R.id.layout_price)
        LinearLayout layoutPrice;
        @BindView(R.id.save_location)
        TextView saveLocation;
        @BindView(R.id.layout_location)
        LinearLayout layoutLocation;
        @BindView(R.id.save_cost)
        TextView saveCost;
        @BindView(R.id.layout_cost)
        LinearLayout layoutCost;
        @BindView(R.id.save_show_more)
        Button saveShowMore;
        @BindView(R.id.save_finsh)
        Button saveFinsh;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }


}

