package com.example.mashaweer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.ui.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.UUID;

public class AddServiceActivity extends AppCompatActivity {
    EditText cost ,price,location , type;
    Button puplish ;

    TextView total;
    private DatabaseReference databaseReferance;
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        puplish  = findViewById(R.id.puplish_btn);
        type  = findViewById(R.id.type);
        location  = findViewById(R.id.location);
        price  =findViewById(R.id.price);
        cost = findViewById(R.id.cost);
        total  =findViewById(R.id.total);
        token = FirebaseInstanceId.getInstance().getToken();

        uid = SharedPreferencesManger.LoadStringData(AddServiceActivity.this, "uid");

        puplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puplishData(type,location,price,cost,total);
            }
        });

    }








    private void puplishData(EditText typeEt, EditText locationEt, EditText priceEt, EditText costEt, TextView totalEt) {


        String type = typeEt.getText().toString();
        String location = locationEt.getText().toString();
        String price = priceEt.getText().toString();
        String cost = costEt.getText().toString();
        int price_ = Integer.parseInt(price);
        int cost_ = Integer.parseInt(cost);
        String total = String.valueOf(price_ + cost_);



        databaseReferance = FirebaseDatabase.getInstance().getReference().child("service");

        String uniqueID = UUID.randomUUID().toString();
        Service serviceData = new Service(uid, location, price, cost, type, total, token, uniqueID);
        boolean successful = databaseReferance.push().setValue(serviceData).isSuccessful();

        if (!successful){
            finish();
        }
        else {

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }



    }
}
