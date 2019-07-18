package com.example.mashaweer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.ui.service.ShowServiceActivity;

import java.util.List;

public class AdapterGetMyService extends RecyclerView.Adapter<AdapterGetMyService.ViewHolder> {

    Context context;
    List<Service> listOfService ;
    private Activity activity;
    private boolean clickd  = true ;


    // private ApiServices apiServices;


    public AdapterGetMyService(Context context, List<Service> listOfService , Activity activity) {
        this.context = context;
        this.listOfService = listOfService;
        this.activity = activity;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_getservice, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {



         final Service data = listOfService.get(i);
        final String cost = data.getCost();
        final String from = data.getFrom();
        final String price = data.getPrice();
        String time = data.getTime();
        viewHolder.time.setText(time);
       String type = data.getType();
        final String to = data.getTo();
    String     token = data.getToken();

        activity.registerForContextMenu(viewHolder.edit);

        if (data.isSpacial()){

            viewHolder.special.setVisibility(View.VISIBLE);
        }else {
            viewHolder.special.setVisibility(View.GONE);
        }

        viewHolder.type.setText(type);
        viewHolder.cost.setText(cost);
        viewHolder.from.setText(from);


        viewHolder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String id = data.getId();

                    SharedPreferencesManger.SaveData(activity,"idItem",id);

                    Intent intent = new Intent(context, ShowServiceActivity.class);

                    intent.putExtra("type",type);
                    intent.putExtra("cost",cost);
                    intent.putExtra("from",from);
                    intent.putExtra("price",price);
                    intent.putExtra("to",to);
                    intent.putExtra("idItem",id);
                    intent.putExtra("token",token);
                    intent.putExtra("user_id",1);
                    activity.startActivity(intent);

                // set Dialog

            }
        });


    }







     @Override
    public int getItemCount() {
        return listOfService.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public TextView from;
        public TextView cost;
        public Button show;
        public TextView  special  , time   , edit;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type =  itemView.findViewById(R.id.type2);
            from =  itemView.findViewById(R.id.location2);
            cost =  itemView.findViewById(R.id.cost2);
            show =  itemView.findViewById(R.id.show_service);
            special = itemView.findViewById(R.id.special);
            time = itemView.findViewById(R.id.time);
            edit =itemView.findViewById(R.id.edit);


        }




    }
}