package com.example.mashaweer.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.ServiceAccept;
import com.example.mashaweer.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdapterGetService extends RecyclerView.Adapter<AdapterGetService.ViewHolder> {

    Context context;
    List<Service> listOfService = new ArrayList<>();
    private String uid;
    private  int id ;
    private DatabaseReference databaseReferance;
    private String idItem;
    private String type;
    private Activity activity;


    // private ApiServices apiServices;


    public AdapterGetService(Context context, List<Service> listOfService , int id , Activity activity) {
        this.context = context;
        this.listOfService = listOfService;
        this.id = id ;
        this.activity = activity;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_getservice, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder,  int i) {

         final Service data = listOfService.get(i);
        String cost = data.getCost();
        String location = data.getLocation();
        String price = data.getPrice();
          type = data.getType();
        String locationDeleverd = data.getLocationDeleverd();
        viewHolder.loctopnDeleverd.setText(locationDeleverd);
         uid = data.getUid();

        if (this.id ==1){

             viewHolder.aplay.setVisibility(View.GONE);
         }



        viewHolder.type.setText(type);
        viewHolder.cost.setText(cost);
        viewHolder.loction.setText(location);
        viewHolder.price.setText(price);
        viewHolder.aplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 idItem = data.getId();

                 SharedPreferencesManger.SaveData(activity,"idItem",idItem);

                pushNotifications();

                pushDataIdToDatabase();


                // set Dialog

            }
        });




    }


    private void pushDataIdToDatabase() {
        databaseReferance = FirebaseDatabase.getInstance().getReference().child("service_aplay");
        String uid2 = SharedPreferencesManger.LoadStringData(activity, "uid");

        ServiceAccept notificatios = new ServiceAccept(idItem,type ,uid , uid2);
        databaseReferance.push().setValue(notificatios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                StyleableToast.makeText(context, "تم ارسال الطلب", Toast.LENGTH_LONG, R.style.mytoast).show();

            }
        });



    }

    private void pushNotifications() {


             AsyncTask.execute(new Runnable() {
                 @Override
                 public void run() {
                     int SDK_INT = android.os.Build.VERSION.SDK_INT;
                     if (SDK_INT > 8) {
                         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                 .permitAll().build();
                         StrictMode.setThreadPolicy(policy);
                         String send_uid;

                         //This is a Simple Logic to Send Notification different Device Programmatically....
                         send_uid = uid ;

                         try {
                             String jsonResponse;

                             URL url = new URL("https://onesignal.com/api/v1/notifications");
                             HttpURLConnection con = (HttpURLConnection) url.openConnection();
                             con.setUseCaches(false);
                             con.setDoOutput(true);
                             con.setDoInput(true);

                             con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                             con.setRequestProperty("Authorization", "Basic N2FiYjVlZDctMzliNS00ZThlLWE5ZWMtYjQ1ZDAwY2VjNzM3");
                             con.setRequestMethod("POST");

                             String strJsonBody = "{"
                                     + "\"app_id\": \"dccdb512-bdbb-4950-a714-95d1599b1ce3\","

                                     + "\"filters\": [{\"field\": \"tag\", \"key\": \"uid\", \"relation\": \"=\", \"value\": \"" + send_uid + "\"}],"

                                     + "\"data\": {\"foo\": \"bar\"},"
                                     + "\"contents\": {\"en\": \"you have accept to your service\"}"
                                     + "}";


                             System.out.println("strJsonBody:\n" + strJsonBody);

                             byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                             con.setFixedLengthStreamingMode(sendBytes.length);

                             OutputStream outputStream = con.getOutputStream();
                             outputStream.write(sendBytes);

                             int httpResponse = con.getResponseCode();
                             System.out.println("httpResponse: " + httpResponse);

                             if (httpResponse >= HttpURLConnection.HTTP_OK
                                     && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                 Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                 jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                 scanner.close();
                             } else {
                                 Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                 jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                 scanner.close();
                             }
                             System.out.println("jsonResponse:\n" + jsonResponse);

                         } catch (Throwable t) {
                             t.printStackTrace();
                         }
                     }
                 }
             });
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
        public Button aplay;
        public TextView loctopnDeleverd ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type =  itemView.findViewById(R.id.type2);
            loction =  itemView.findViewById(R.id.location2);
            price =  itemView.findViewById(R.id.price2);
            cost =  itemView.findViewById(R.id.cost2);
            aplay =  itemView.findViewById(R.id.aply_btn);
            loctopnDeleverd =  itemView.findViewById(R.id.location_dlever2);

        }
    }
}