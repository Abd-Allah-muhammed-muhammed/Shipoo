package com.example.mashaweer.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActifity extends AppCompatActivity {

    private String token;

    EditText mName  , mAddress , mEmail , mPhone , mPass , mRepass ;
    Button edit_btn;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actifity);

        mAddress = findViewById(R.id.profile_address);
        mEmail = findViewById(R.id.profile_mail);
        mName = findViewById(R.id.profile_name);
        mPhone = findViewById(R.id.profile_phone);
        mPass = findViewById(R.id.profile_pass);
        mRepass = findViewById(R.id.profile_re_pass);
         token = SharedPreferencesManger.LoadStringData(ProfileActifity.this, "token");

        Query query = FirebaseDatabase.getInstance().getReference().child("Users");
        query.orderByChild("token").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {


                    Users value = snapshot.getValue(Users.class);
                    String mail = value.getMail();
                    String name = value.getName();
                    String address = value.getAddress();
                    String phone = value.getPhone();
                    float rotate = value.getRotate();
                    String pass = value.getPass();
                    String repass = value.getRepass();

                    mAddress.setText(address);
                    mEmail.setText(mail);
                    mName.setText(name);
                    mPhone.setText(phone);
                    mPass.setText(pass);
                    mRepass.setText(repass);




                }
            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
