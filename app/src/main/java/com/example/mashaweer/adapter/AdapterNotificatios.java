package com.example.mashaweer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashaweer.R;
import com.example.mashaweer.model.AplayAcscept;
import com.example.mashaweer.model.Notificatios;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.ServiceAccept;
import com.example.mashaweer.model.Singup;
import com.example.mashaweer.ui.HomeActivity;
import com.example.mashaweer.ui.SowInfoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AdapterNotificatios extends RecyclerView.Adapter<AdapterNotificatios.ViewHolder> {

    Context context;
    List<ServiceAccept> listOfnotifications = new ArrayList<>();
    public static  String idItem ;
    private String uid2;
    Activity activity ;
    private DatabaseReference databaseReferance;
    private String uid;


    // private ApiServices apiServices;


    public AdapterNotificatios(Context context, List<ServiceAccept> listOfnotifications , Activity activity) {
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
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder,  int i) {

        ServiceAccept data = listOfnotifications.get(i);
        String type = data.getType();
        viewHolder.titel.setText( " aplay to your service: "+type);
         idItem = data.getIdItem();
        uid2 = data.getUid2();
         uid = data.getUid();



        viewHolder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pushnotification();
                pushtDeal();

                final Query query = FirebaseDatabase.getInstance().getReference().child("users");
                query.orderByChild("uid").equalTo(uid2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @java.lang.Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Singup clint = snapshot.getValue(Singup.class);

                            String name = clint.getName();
                            String address = clint.getAddress();
                            String mail = clint.getMail();
                            String phone = clint.getPhone();


                            Intent intent = new Intent(context, SowInfoActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("mail", mail);
                            intent.putExtra("phone", phone);
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

        viewHolder.dont_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletItem();
            }
        });

    }

    private void deletItem() {
        Query query = FirebaseDatabase.getInstance().getReference().child("service_aplay");
        query.orderByChild("idItem").equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void pushtDeal() {

        databaseReferance = FirebaseDatabase.getInstance().getReference().child("deal");
        AplayAcscept serviceAccept = new AplayAcscept(idItem,uid);
        databaseReferance.push().setValue(serviceAccept);


    }

    private void pushnotification() {

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
                        send_uid = uid2 ;

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
                                    + "\"contents\": {\"en\": \"you have accept to your aplay\"}"
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