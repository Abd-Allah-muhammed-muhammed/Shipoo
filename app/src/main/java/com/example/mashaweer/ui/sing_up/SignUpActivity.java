package com.example.mashaweer.ui.sing_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.ui.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    EditText email , phone , name   ;

    Button singUp  ;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String number;
    private SweetAlertDialog pDialog;
    private int id_user_type;
    private int SEND_SMS_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.ID_Email);

        phone = findViewById(R.id.ID_Phone);

        singUp = findViewById(R.id.btn_Register);

        name = findViewById(R.id.ID_Name);

          pDialog = new SweetAlertDialog(this);

showDialogId();




        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id_user_type!=1&&id_user_type!=2){

                    showDialogId();
                }


                if (email.getText().toString().matches("")){

                    email.setError("يتطلب اميل ");
                }else if (phone.getText().toString().matches("")){
                    phone.setError("يتطلب رقم موبايل");

                }else if (name.getText().toString().matches("")){

                    name.setError("يتطلب اسمك");

                }else {

                    pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    verificationCode();
                }


            }
        });





        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // a1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
            }



            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("11", "onVerificationFailed", e);
                phone.setError(e.getMessage());
                pDialog.cancel();
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    phone.setError(e.getMessage());
                    pDialog.cancel();
                    // Invalid request
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    pDialog.cancel();

                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                mVerificationId = verificationId;
                StyleableToast.makeText(SignUpActivity.this, "تم ارسال الكود", Toast.LENGTH_LONG, R.style.mytoast).show();


                Intent intent = new Intent(SignUpActivity.this,VerificationCodeActifity.class);

                intent.putExtra("number" , phone.getText().toString().trim());
                intent.putExtra("name" , name.getText().toString());
                intent.putExtra("mail",email.getText().toString());
                intent.putExtra("verificatioId",mVerificationId);
                intent.putExtra("id_user_type",id_user_type);

                checkPermission();

                pDialog.cancel();
                startActivity(intent);





            }
        };
        // [END phone_auth_callbacks]
    }

    private void checkPermission() {

        //Requesting permission


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_SMS },
                    SEND_SMS_CODE);


    }

    private void showDialogId() {

        final Dialog idDialog = new Dialog(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.id_user_dialog, null);
        idDialog.setContentView(view);
        idDialog.show();


        final Button add_1 = view.findViewById(R.id.add_service);

        final Button get_2 = view.findViewById(R.id.get_service);

        add_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id_user_type = 1;
                idDialog.cancel();
            }
        });

        get_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_user_type = 2 ;

                idDialog.cancel();
            }
        });

    }


    private void verificationCode() {


        number = "+2"+phone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, SignUpActivity.this, mCallbacks);

    }

//

//    }






    }








