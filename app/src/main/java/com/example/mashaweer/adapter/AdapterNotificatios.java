package com.example.mashaweer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.model.Notificatios;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.ServiceAccept;

import java.util.ArrayList;
import java.util.List;



public class AdapterNotificatios extends RecyclerView.Adapter<AdapterNotificatios.ViewHolder> {

    Context context;
    List<ServiceAccept> listOfnotifications = new ArrayList<>();
    public static  String idItem ;


    // private ApiServices apiServices;


    public AdapterNotificatios(Context context, List<ServiceAccept> listOfnotifications) {
        this.context = context;
        this.listOfnotifications = listOfnotifications;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notifications, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder,  int i) {

        ServiceAccept data = listOfnotifications.get(i);
        String type = data.getType();
        viewHolder.titel.setText( " aplay to your service"+type);
         idItem = data.getIdItem();


        viewHolder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.dont_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return listOfnotifications.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titel;
        public Button agree , dont_agree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titel =  itemView.findViewById(R.id.titel_noti);
            dont_agree =  itemView.findViewById(R.id.dont_agree);
            agree =  itemView.findViewById(R.id.agree);


        }
    }
}