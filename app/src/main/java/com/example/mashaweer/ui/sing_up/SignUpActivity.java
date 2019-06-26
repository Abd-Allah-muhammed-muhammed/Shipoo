package com.example.mashaweer.ui.sing_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

    private int user_id;
    private FirebaseAuth mAuth;

    EditText email , phone , name   ;

//
    Button singUp  ;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String number;
    private SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.ID_Email);
//
        phone = findViewById(R.id.ID_Phone);
//
        singUp = findViewById(R.id.btn_Register);
//
        name = findViewById(R.id.ID_Name);
         user_id = getIntent().getIntExtra("user_id",0);

          pDialog = new SweetAlertDialog(this);
//

//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//
//
//            }
//
//        };
//
//        thread.start();
//




        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                // 1 - Instant verification. In some cases the phone number can be instantly
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
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
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

                intent.putExtra("number" , number);
                intent.putExtra("name" , name.getText().toString());
                intent.putExtra("user_id",user_id);
                intent.putExtra("mail",email.getText().toString());
                intent.putExtra("verificatioId",mVerificationId);

                pDialog.cancel();
                startActivity(intent);





            }
        };
        // [END phone_auth_callbacks]
    }


    private void verificationCode() {


        number = "+2"+phone.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, SignUpActivity.this, mCallbacks);

    }

//

//    }






    }








