package com.example.mashaweer.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterGetMyService;
import com.example.mashaweer.adapter.AdapterGetService;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.example.mashaweer.model.AcceptService;
import com.example.mashaweer.ui.SowInfoActivity;
import com.example.mashaweer.ui.service.AddServiceActivity;
import com.example.mashaweer.ui.LoginActivity;
import com.example.mashaweer.ui.notifications.NotificationUserActivity;
import com.example.mashaweer.ui.profile.ProfileActifity;
import com.example.mashaweer.ui.service.SavedInfoActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

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
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Intent intent;

    private Button btnNotification;
    private TextView toolbarType, notiCount  ,refresh;
    private DatabaseReference databaseReferance;
    private String token;
    private int requestcode = 1;
    private List<Service> listofDataService = new ArrayList<>();
    private RecyclerView my_services_rv;
    private AdapterGetMyService adapterGetMyService;
    private List<AcceptService> listofData = new ArrayList<>();
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton add_fb, special;
    Animation rotateAnimation;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        btnNotification = findViewById(R.id.notification_btn_home);
        toolbarType = findViewById(R.id.toolbar_type_home);
        my_services_rv = findViewById(R.id.my_services_rv);
        notiCount = findViewById(R.id.textContNotification);
        floatingActionMenu = findViewById(R.id.fab);
        refresh = findViewById(R.id.refresh_home);

        add_fb = findViewById(R.id.add_fb);
        special = findViewById(R.id.add_fb_special);
        token = SharedPreferencesManger.LoadStringData(HomeActivity.this, "token");



        if (listofData.size()<=0){

            notiCount.setVisibility(View.GONE);
        }else {
            notiCount.setVisibility(View.VISIBLE);

        }
        listofDataService.clear();
        listofData.clear();
        getMyservice();
        getnotifications(token);



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rotateAnimation();

            }
        });






        add_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            toolbarType.setText("اضافة خدمة");


                intent = new Intent(HomeActivity.this, AddServiceActivity.class);
                startActivity(intent);
                //add service data to firebase with uid user

            }
        });
        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(HomeActivity.this, AddServiceActivity.class);
                intent.putExtra("special", true);
                startActivity(intent);
            }
        });



        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        checkPermissions();


        toolbarType.setText("خدماتي");


        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(HomeActivity.this, NotificationUserActivity.class);
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
        refresh.startAnimation(rotateAnimation);
        listofDataService.clear();
        listofData.clear();
        getMyservice();
        getnotifications(token);


    }






    private void getMyservice() {


        Query query = FirebaseDatabase.getInstance().getReference().child("Service");
        query.orderByChild("token").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Service value = snapshot.getValue(Service.class);
                    listofDataService.add(value);
                    my_services_rv.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    adapterGetMyService = new AdapterGetMyService(HomeActivity.this, listofDataService,  HomeActivity.this);
                    my_services_rv.setAdapter(adapterGetMyService);
                    adapterGetMyService.notifyDataSetChanged();
//                    for (int i = 0; i < listofDataService.size(); i++) {
//                        adapterGetMyService.notifyItemRemoved(i);
//                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

//            super.onBackPressed();


            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);


        }

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

            intent = new Intent(HomeActivity.this, ProfileActifity.class);
            intent.putExtra("user_id", 1);
            startActivity(intent);


        } else if (id == R.id.notificatin_nav) {

            intent = new Intent(HomeActivity.this, NotificationUserActivity.class);

            startActivity(intent);

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

            SharedPreferencesManger.clean(HomeActivity.this);
            intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();

        }else if (id==R.id.info_mandoop_nav){


            intent = new Intent(HomeActivity.this, SavedInfoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestcode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 1 && requestCode == requestcode) {

        } else {
            checkPermissions();
        }
    }


    private void getnotifications(final String token) {


        Query query = FirebaseDatabase.getInstance().getReference().child("AcceptService");

        query.orderByChild("tokenUser").equalTo(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @java.lang.Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AcceptService value = snapshot.getValue(AcceptService.class);

                    listofData.add(value);



                }


                int count = listofData.size();


                    if (count <=0) {
                        notiCount.setVisibility(View.GONE);

                    }else {

                        notiCount.setVisibility(View.VISIBLE);
                        notiCount.setText("" + count);
                    }




            }

            @java.lang.Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
