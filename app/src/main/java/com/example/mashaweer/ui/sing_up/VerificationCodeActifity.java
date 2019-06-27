package com.example.mashaweer.ui.sing_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.MessageListener;
import com.example.mashaweer.helper.MessageReceiver;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Singup;
import com.example.mashaweer.ui.HomeActivity;
import com.example.mashaweer.ui.SecondHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VerificationCodeActifity extends AppCompatActivity implements MessageListener {

    EditText verificationCode;
    Button getVerificationCode_Btn  , finish_sing_up;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private int user_id;
    EditText pass,address ,confirmPass , repass   ;
    private String mail_  , phone_  ,name_  ,address_  ;
    private String token;
    private DatabaseReference databaseReferance;
    private String uid;

    LinearLayout layout_finish , layout_chick;
    private SweetAlertDialog pDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code_actifity);

        verificationCode = findViewById(R.id.verification_code_et);
        getVerificationCode_Btn = findViewById(R.id.getVerificationCode_Btn);
        address = findViewById(R.id.ID_address);
        confirmPass = findViewById(R.id.ID_RePassword);
        pass = findViewById(R.id.ID_Password);
        finish_sing_up = findViewById(R.id.finish_sing_up);
        layout_finish = findViewById(R.id.layout_finish);
        layout_chick = findViewById(R.id.layout_chick);
        layout_chick.setVisibility(View.VISIBLE);
        layout_finish.setVisibility(View.GONE);
        getData();


//Register sms listener
        MessageReceiver.bindListener(this);




        pDialog = new SweetAlertDialog(this);

        finish_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();
            }
        });
        user_id = getIntent().getIntExtra("user_id", 0);
        mAuth = FirebaseAuth.getInstance();

        // get token

        token = FirebaseInstanceId.getInstance().getToken();


        Thread thread = new Thread() {
            @Override
            public void run() {

                getVerificationCode_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (verificationCode.getText().toString().matches("")){

                            verificationCode.setError("من فضلك ادخل الكود");

                        }else {
                            verification_Code();
                        }


                    }
                });

                }

        };

        thread.start();


        databaseReferance = FirebaseDatabase.getInstance().getReference().child("users");




    }

    private void sendData() {


        pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferencesManger.SaveData(VerificationCodeActifity.this,"uid",uid);

        String addressT = address.getText().toString();
        String passT = pass.getText().toString();
        String repass = confirmPass.getText().toString();


        Singup singup = new Singup(name_,phone_,addressT,passT,repass,mail_,uid ,token ,0);

        databaseReferance.push().setValue(singup).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                OneSignal.sendTag("uid", uid);
                Toast.makeText(VerificationCodeActifity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                pDialog.cancel();
            }
        });




        if (user_id == 1) {

            Intent intent = new Intent(VerificationCodeActifity.this, HomeActivity.class);
            startActivity(intent);
        } else {

            Intent intent = new Intent(VerificationCodeActifity.this, SecondHomeActivity.class);
            startActivity(intent);
        }

    }


    private void verification_Code() {
        pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        String code = verificationCode.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);

    }



    private void getData() {


        Intent intent = getIntent();
        name_ = intent.getStringExtra("name");
        address_ = intent.getStringExtra("address");
        phone_ = intent.getStringExtra("number");
        mail_ = intent.getStringExtra("mail");
        user_id = intent.getIntExtra("user_id", 0);
        mVerificationId = intent.getStringExtra("verificatioId");



    }




    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                             uid = user.getUid();

                             pDialog.cancel();
                            layout_chick.setVisibility(View.GONE);
                            layout_finish.setVisibility(View.VISIBLE);


                        } else {
                            pDialog.cancel();
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                verificationCode.setError("Invalid code.");
                                // [END_EXCLUDE]

                                pDialog.dismiss();
                            }

                        }
                    }
                });
    }


    @Override
    public void messageReceived(String message) {

        String[] separated = message.split("is");

     String code =  separated[0].trim();

        verificationCode.setText(code);

    }
}



