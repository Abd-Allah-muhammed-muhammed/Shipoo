package com.example.mashaweer.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.mashaweer.R;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Singup;
import com.example.mashaweer.ui.SowInfoActivity2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActifity extends AppCompatActivity {

    private String uid;

    EditText mName  , mAddress , mEmail , mPhone , mPass , mRepass ;
    RatingBar  ratingBar ;
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
        ratingBar = findViewById(R.id.rating_bar);
         uid = SharedPreferencesManger.LoadStringData(ProfileActifity.this, "uid");
         user_id = getIntent().getIntExtra("user_id", 0);


        if (user_id== 1 ){

            ratingBar.setVisibility(View.GONE);


        }

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {


                    Singup value = snapshot.getValue(Singup.class);
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
                    ratingBar.setRating(rotate);




                }
            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
