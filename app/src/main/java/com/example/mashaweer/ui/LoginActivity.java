package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Singup;
import com.example.mashaweer.ui.sing_up.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.onesignal.OneSignal;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private int user_id;
    private boolean loginTrue;
    private Button loginBtn;
    private EditText phone, pass;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseFirebase;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_button);
        phone = findViewById(R.id.id_phone);
        pass = findViewById(R.id.idpass);


        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        loginTrue = false;
        mAuth = FirebaseAuth.getInstance();

        user_id = getIntent().getIntExtra("user_id", 0);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();


            }
        });


    }

    private void login() {

          pDialog = new SweetAlertDialog(this);
      //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
       // pDialog.setTitleText("Loading");
       // pDialog.setCancelable(false);



        if (phone.getText().toString().matches("")){
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#CE3131"));
            pDialog.setTitleText("enter your phone number");
            pDialog.setCancelable(true);
            pDialog.show();


        }else {
            pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
             pDialog.setTitleText("Loading");
             pDialog.setCancelable(false);
            pDialog.show();
            Query query = FirebaseDatabase.getInstance().getReference().child("users");

            query.orderByChild("phone").equalTo(phone.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()
                    ) {


                        Singup value = snapshot.getValue(Singup.class);

                        String phone_ = value.getPhone();
                        String pass_ = value.getPass();
                        String uid = value.getUid();
                        OneSignal.sendTag("uid", uid);
                        SharedPreferencesManger.SaveData(LoginActivity.this, "uid", uid);
                        if (pass_.equals(pass.getText().toString())&&phone_.equals(phone.getText().toString())){

                            if (user_id == 1) {
                                StyleableToast.makeText(LoginActivity.this, "تم تسجيل الدخوب بنجاح", Toast.LENGTH_LONG, R.style.mytoast).show();
                                pDialog.cancel();
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

                            } else {
                                pDialog.cancel();
                                intent = new Intent(LoginActivity.this, SecondHomeActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#CE3131"));
                            pDialog.setTitleText("chick your phone or password");
                            pDialog.setCancelable(true);
                            pDialog.show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });


        }










    // ...
}


    public void singUp(View view) {
        intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("user_id", 1);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);
    }
}
