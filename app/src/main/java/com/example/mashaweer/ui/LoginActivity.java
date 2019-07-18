package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Users;
import com.example.mashaweer.ui.home.HomeActivity;
import com.example.mashaweer.ui.home.SecondHomeActivity;
import com.example.mashaweer.ui.sing_up.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.muddzdev.styleabletoast.StyleableToast;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.mashaweer.helper.SharedPreferencesManger.SaveData;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private Button loginBtn ,sign_button;
    private EditText phone, pass;
    private SweetAlertDialog pDialog;
    private String phonesh , passSh  ,token;
  //  private String token ;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_button);
        phone = findViewById(R.id.id_phone);
        sign_button = findViewById(R.id.sign_button);
        pass = findViewById(R.id.idpass);
        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                singUp();
            }
        });


     token = FirebaseInstanceId.getInstance().getToken();

        Log.d("token", "token is :"+token);

        try {
            phonesh = SharedPreferencesManger.LoadStringData(this, "phone");
            passSh = SharedPreferencesManger.LoadStringData(this, "pass");
            id = SharedPreferencesManger.LoadIntegerData(LoginActivity.this,"id");

            if (phonesh.isEmpty()) {


            } else {

                 if (id == 1) {
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else if (id==2){
                    intent = new Intent(LoginActivity.this, SecondHomeActivity.class);
                    startActivity(intent);
                }
            }

        } catch (Exception e) {


        }



        // get the value from database  to know which user want to login


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pass.getText().toString().matches("")){

                    pass.setError("ادخل الرقم السري");
                }else if (phone.getText().toString().matches("")){


                    phone.setError("ادخل رقم الموبايل ");
                }else {

                    pDialog = new SweetAlertDialog(LoginActivity.this);
                    login();
                }





            }
        });


    }


    private void saveDataUser() {

        if (phone.getText().toString().matches("") && pass.getText().toString().matches("")) {

            StyleableToast.makeText(LoginActivity.this, "املاء البيانات اولا", Toast.LENGTH_LONG, R.style.mytoast).show();

        } else {
            SaveData(LoginActivity.this, "phone", phone.getText().toString());
            SaveData(LoginActivity.this, "pass", pass.getText().toString());

        }

    }

    private void login() {

            pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean exists = dataSnapshot.exists();
                if (exists){
                    Query query = FirebaseDatabase.getInstance().getReference().child("Users");
                    query.orderByChild("phone").equalTo(phone.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()
                            ) {

                                Users value = snapshot.getValue(Users.class);

                                String phone_ = value.getPhone();
                                String pass_ = value.getPass();
                                String mtoken = value.getToken();


                                if (token.equals(mtoken)){



                                int  id = value.getId();
                                SharedPreferencesManger.SaveData(LoginActivity.this,"id",id);

                                SaveData(LoginActivity.this, "token", mtoken);
                                if (pass_.equals(pass.getText().toString()) && phone_.equals(phone.getText().toString())) {

                                    if (id == 1) {


                                        pDialog.cancel();
                                        saveDataUser();
                                        intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    } else {

                                        pDialog.cancel();
                                        saveDataUser();

                                        intent = new Intent(LoginActivity.this, SecondHomeActivity.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    phone.setError("لا يوجد حساب بهذا الرقمك ");
                                    pDialog.cancel();

                                }
                            }else {

                                    Toast.makeText(LoginActivity.this, "يرجي انشاء حساب جديد لانك قمت بتغير الهاتف", Toast.LENGTH_SHORT).show();
                                    pDialog.cancel();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            pDialog.dismiss();
                        }


                    });

                }else {
                    phone.setError("لا يوجد حساب بهذا الرقمك ");
                    pDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pDialog.cancel();

            }
        });


        }

        // ...

    public void singUp() {
        intent = new Intent(LoginActivity.this, SignUpActivity.class);
    startActivity(intent);
}




    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("الخروج من البرنامج");
        alertDialogBuilder
                .setMessage("اضغط للخروج")
                .setCancelable(false)
                .setPositiveButton("نعم",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}


