package com.example.mashaweer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private int user_id;
    private boolean loginTrue ;
    private Button loginBtn;
    private EditText email , pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_button);
        email =findViewById(R.id.id_email);
        pass =findViewById(R.id.idpass);


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

        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("11", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            OneSignal.sendTag("uid",uid);
                            SharedPreferencesManger.SaveData(LoginActivity.this,"uid",uid);


                            if (user_id == 1) {
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

                            } else {
                                intent = new Intent(LoginActivity.this, SecondHomeActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("11", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    public void singUp(View view) {
        intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("user_id", 1);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        user.updateEmail(email.getText().toString());
        user.updatePassword(pass.getText().toString());
    }
}
