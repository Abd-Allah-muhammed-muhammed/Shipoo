package com.example.mashaweer.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AcceptService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowServiceActivity extends AppCompatActivity {

    @BindView(R.id.type_sh)
    TextView ShowServiceType;
    @BindView(R.id.location_sh)
    TextView ShowServiceFrom;
    @BindView(R.id.location_dlever_sh)
    TextView ShowServiceTo;
    @BindView(R.id.price_sh)
    TextView ShowServicePrice;
    @BindView(R.id.cost_sh)
    TextView ShowServiceCost;
    @BindView(R.id.Show_service_puplish_btn)
    Button ShowServicePuplishBtn;


    private DatabaseReference databaseReferance;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service);
        ButterKnife.bind(this);





        Intent dataIntent = getIntent();
        int user_id = dataIntent.getIntExtra("user_id", 0);
      String  type = dataIntent.getStringExtra("type");
        String cost = dataIntent.getStringExtra("cost");
        String from = dataIntent.getStringExtra("from");
        String price = dataIntent.getStringExtra("price");
        String to = dataIntent.getStringExtra("to");
        String  idItem = dataIntent.getStringExtra("idItem");
        token = dataIntent.getStringExtra("token");

        ShowServiceCost.setText(cost);
        ShowServiceType.setText(type);
        ShowServiceFrom.setText(from);
        ShowServiceTo.setText(to);
        ShowServicePrice.setText(price);

        if (user_id==1) {

            ShowServicePuplishBtn.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.Show_service_puplish_btn)
    public void onViewClicked() {

        pushDataIdToDatabase();
        pushNotifications();

    }


    private void pushDataIdToDatabase() {

        Intent intent = getIntent();
        String  idItem = intent.getStringExtra("idItem");
        String  type = intent.getStringExtra("type");

        databaseReferance = FirebaseDatabase.getInstance().getReference().child("AcceptService");
        String mToken = SharedPreferencesManger.LoadStringData(this, "token");

        AcceptService notificatios = new AcceptService(idItem,type , mToken ,token);
        databaseReferance.push().setValue(notificatios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                StyleableToast.makeText(ShowServiceActivity.this, "تم ارسال الطلب", Toast.LENGTH_LONG, R.style.mytoast).show();

                finish();

            }
        });



    }

    private void pushNotifications() {

    }





}
