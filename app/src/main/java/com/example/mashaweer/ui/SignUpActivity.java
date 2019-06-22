package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private int user_id;
    private FirebaseAuth mAuth;
    EditText name ,phone , email ,pass,address ,confirmPass ;
    Button singUp ;
    private DatabaseReference databaseReferance;
    Spinner spinner ;
    private String city;
    private String token;


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
