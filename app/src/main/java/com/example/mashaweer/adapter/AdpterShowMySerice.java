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
import com.example.mashaweer.model.Service;

import java.util.ArrayList;
import java.util.List;

public class AdpterShowMySerice extends RecyclerView.Adapter<AdpterShowMySerice.ViewHolder> {

    Context context;
    List<Service> listOfService = new ArrayList<>();


    // private ApiServices apiServices;


    public AdpterShowMySerice(Context context, List<Service> listOfService) {
        this.context = context;
        this.listOfService = listOfService;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_services, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder,  int i) {

        Service data = listOfService.get(i);
        String cost = data.getCost();
        String location = data.getLocation();
        String price = data.getPrice();
        String type = data.getType();
        String total = data.getTotal();

        viewHolder.type.setText(type);
        viewHolder.cost.setText(cost);
        viewHolder.loction.setText(location);
        viewHolder.price.setText(price);
        viewHolder.total.setText(total);
        viewHolder.puplish.setVisibility(View.GONE);
        
    }


    @Override
    public int getItemCount() {
        return listOfService.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public TextView loction;
        public TextView price;
        public TextView cost;
        public TextView total;
        public Button puplish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type =  itemView.findViewById(R.id.type);
            loction =  itemView.findViewById(R.id.location);
            price =  itemView.findViewById(R.id.price);
            cost =  itemView.findViewById(R.id.cost);
            total =  itemView.findViewById(R.id.total);
            puplish =  itemView.findViewById(R.id.puplish_btn);

        }
    }
}