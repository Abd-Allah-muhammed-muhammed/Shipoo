package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Singup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private int user_id;
    private FirebaseAuth mAuth;
    EditText name ,phone , email ,pass,address ,confirmPass  , verificationCode;
    Button singUp  , getVerificationCode_Btn;
    private DatabaseReference databaseReferance;
    Spinner spinner ;
    private String city;
    private String token;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private CountryCodePicker ccp;
    private String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.ID_Email);
        pass = findViewById(R.id.ID_Password);
        phone = findViewById(R.id.ID_Phone);
        address = findViewById(R.id.ID_address);
        name = findViewById(R.id.ID_Name);
        confirmPass = findViewById(R.id.ID_RePassword);
        spinner = findViewById(R.id.Spn_city);
        singUp = findViewById(R.id.btn_Register);
        getVerificationCode_Btn = findViewById(R.id.verification_code_btn);
        verificationCode = findViewById(R.id.verification_code_et);


        user_id = getIntent().getIntExtra("user_id", 0);
        mAuth = FirebaseAuth.getInstance();

        // get token

        token = FirebaseInstanceId.getInstance().getToken();

        getCities();
        databaseReferance = FirebaseDatabase.getInstance().getReference().child("users");

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                singUp();





            }
        });


        getVerificationCode_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                number = "+2"+phone.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, SignUpActivity.this, mCallbacks);


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

                String smsCode = credential.getSmsCode();
              //  chickCode(smsCode);
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                signInWithPhoneAuthCredential(credential);
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
                    phone.setError("Invalid phone number.");
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



                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
        // [END phone_auth_callbacks]
    }

//    private void chickCode(String smsCode) {
//
//        String code = verificationCode.getText().toString();
//
//        if (smsCode.equals(code)){
//
//
//            singupp();
//        }
//    }
//
//    private void singupp() {
//
//
//    }


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            String uid = user.getUid();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("11", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                getVerificationCode_Btn.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }

                        }
                    }
                });

    }





    private void getCities() {


        List<String> nameCity = new ArrayList<>();

        nameCity.add("اختار المدينه");


        final List<String> data = new ArrayList<>();
        data.add("chose your city");
        data.add("mansoura");
        data.add("cairo");
// set Adapter spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(SignUpActivity.this,
                android.R.layout.simple_spinner_item, data);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {


                    city = data.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }






    private void singUp() {




        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            OneSignal.sendTag("uid", uid);
                            SharedPreferencesManger.SaveData(SignUpActivity.this,"uid",uid);

                            String maileT = email.getText().toString();
                            String phoneT = phone.getText().toString();
                            String nameT = name.getText().toString();
                            String addressT = address.getText().toString();
                            String passT = pass.getText().toString();
                            String repass = confirmPass.getText().toString();


                            Singup singup = new Singup(nameT,phoneT,addressT,passT,repass,maileT,uid ,city,token);

                            databaseReferance.push().setValue(singup);
                            Toast.makeText(SignUpActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                            if (user_id==1){

                                Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }else {

                                Intent intent = new Intent(SignUpActivity.this,SecondHomeActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }

                });
    }







}
