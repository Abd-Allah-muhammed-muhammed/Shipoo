package com.example.mashaweer.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.mashaweer.R;
import com.example.mashaweer.adapter.AdapterGetService;
import com.example.mashaweer.helper.SharedPreferencesManger;
import com.example.mashaweer.model.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
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
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int user_id;
    private Intent intent;

    private Button btnNotification;
    private TextView toolbarType;
    private DatabaseReference databaseReferance;
    private String uid;
    private int requestcode = 1;
    private List <Service> listofDataService = new ArrayList<>();
    private RecyclerView  my_services_rv ;
    private AdapterGetService adapterGetService;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        btnNotification = findViewById(R.id.notification_btn_home);
        toolbarType =findViewById(R.id.toolbar_type_home);
        my_services_rv = findViewById(R.id.my_services_rv);


        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        checkPermissions();

        token = FirebaseInstanceId.getInstance().getToken();

         uid = SharedPreferencesManger.LoadStringData(HomeActivity.this, "uid");

        toolbarType.setText("My Services");

        getMyservice();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarType.setText("Add Service");
              shoewDialog();

              //add service data to firebase with uid user


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

    private void getMyservice() {



        Query query = FirebaseDatabase.getInstance().getReference().child("service");
        query.orderByChild("uid").equalTo(this.uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Service value = snapshot.getValue(Service.class);
                    listofDataService.add(value);
                    my_services_rv.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    adapterGetService = new AdapterGetService(HomeActivity.this ,listofDataService , 1 ,HomeActivity.this);
                    my_services_rv.setAdapter(adapterGetService);
                    adapterGetService.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void shoewDialog() {

        final Dialog rateDialog = new Dialog(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.item_my_services, null);
        rateDialog.setContentView(view);

            rateDialog.show();

       final Button puplish = view.findViewById(R.id.puplish_btn);
        final EditText type = view.findViewById(R.id.type);
        final EditText location = view.findViewById(R.id.location);
        final EditText price = view.findViewById(R.id.price);
        final EditText cost = view.findViewById(R.id.cost);
        final TextView total = view.findViewById(R.id.total);

        puplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                puplishData(type,location,price,cost,total);
                adapterGetService.notifyDataSetChanged();

                rateDialog.cancel();


            }
        });
    }

    private void puplishData(EditText typeEt, EditText locationEt, EditText priceEt, EditText costEt, TextView totalEt) {




        String type = typeEt.getText().toString();
        String location = locationEt.getText().toString();
        String price = priceEt.getText().toString();
        String cost = costEt.getText().toString();
        int price_ = Integer.parseInt(price);
        int cost_ = Integer.parseInt(cost);
        String total = String.valueOf(price_ + cost_);


        databaseReferance = FirebaseDatabase.getInstance().getReference().child("service");

        String uniqueID = UUID.randomUUID().toString();
        Service serviceData = new Service(uid,location,price,cost,type,total,token, uniqueID);
        databaseReferance.push().setValue(serviceData);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.notificatin_nav) {

            intent = new Intent(HomeActivity.this , NotificationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, requestcode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length>1&&requestCode==requestcode){

        }else {
            checkPermissions();
        }
    }
}
