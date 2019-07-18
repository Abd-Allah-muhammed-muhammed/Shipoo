package com.example.mashaweer.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterGetService;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.AcceptMandoop;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.ui.LoginActivity;
import com.example.mashaweer.ui.SowInfoActivity;
import com.example.mashaweer.ui.SowInfoActivity2;
import com.example.mashaweer.ui.notifications.NotificationMandoopActivity;
import com.example.mashaweer.ui.profile.ProfileActifity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.mashaweer.ui.service.SavedInfoActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecondHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnNotification;
    private TextView toolbarType , refresh2;
    private DatabaseReference databaseReferance;
    private List<Service> listofDataService = new ArrayList<>() ;
    private RecyclerView get_service_rv ;
    private AdapterGetService adapterGetService;
    private Intent intent;
    private  TextView countNoti ;
    private String token;
    private List<AcceptMandoop> listofDeal = new ArrayList<>();
    Animation rotateAnimation;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_home);
         btnNotification = findViewById(R.id.notification__btn);
         toolbarType =findViewById(R.id.toolbar_type);
        Toolbar toolbar = findViewById(R.id.toolbar);
        countNoti = findViewById(R.id.textContNotification2);
        refresh2 = findViewById(R.id.refresh_second_home);

        get_service_rv = findViewById(R.id.get_service_rv);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbarType.setText("الطلبات");

        token = SharedPreferencesManger.LoadStringData(SecondHomeActivity.this, "token");


        if (listofDeal.size()<=0){

            countNoti.setVisibility(View.GONE);
        }else {

            countNoti.setVisibility(View.VISIBLE);

        }
        listofDataService.clear();
        listofDeal.clear();
        getMyservice();
        getnotifications(token);

        refresh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rotateAnimation();

            }
        });

;

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(SecondHomeActivity.this, NotificationMandoopActivity.class);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void rotateAnimation() {

        rotateAnimation= AnimationUtils.loadAnimation(this,R.anim.rotate);
        refresh2.startAnimation(rotateAnimation);
        listofDataService.clear();
        listofDeal.clear();
        getMyservice();
        getnotifications(token);


    }

    private void getnotifications(String token) {

        Query query = FirebaseDatabase.getInstance().getReference().child("AcceptMandoop");

        query.orderByChild("tokenMandoop").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AcceptMandoop value = snapshot.getValue(AcceptMandoop.class);


                    listofDeal.add(value);

                }

                int count = listofDeal.size();
                                    if (count <=0) {
                        countNoti.setVisibility(View.GONE);

                    }else {
                                        countNoti.setVisibility(View.VISIBLE);

                                        countNoti.setText("" + count);
                                    }


            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SecondHomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.second_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile2) {

            intent = new Intent(SecondHomeActivity.this, ProfileActifity.class);

            intent.putExtra("user_id",2);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.notificatin_nav2) {
            Intent intent = new Intent(SecondHomeActivity.this, NotificationMandoopActivity.class);
            startActivity(intent);



        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


            SharedPreferencesManger.clean(SecondHomeActivity.this);
            intent = new Intent(SecondHomeActivity.this, LoginActivity.class);
            finish();
         }else if (id==R.id.info_mandoop_nav2){


        intent = new Intent(SecondHomeActivity.this, SavedInfoActivity.class);
        startActivity(intent);
    }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getMyservice() {

        databaseReferance = FirebaseDatabase.getInstance().getReference().child("Service");

        databaseReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Service value = snapshot.getValue(Service.class);
                    listofDataService.add(value);
                    get_service_rv.setLayoutManager(new LinearLayoutManager(SecondHomeActivity.this));
                    adapterGetService = new AdapterGetService(SecondHomeActivity.this ,listofDataService  , SecondHomeActivity.this);
                    get_service_rv.setAdapter(adapterGetService);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
