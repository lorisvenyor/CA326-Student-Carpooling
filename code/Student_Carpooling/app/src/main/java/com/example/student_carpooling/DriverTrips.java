package com.example.student_carpooling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.student_carpooling.tripRecyclerView.Trip;
import com.example.student_carpooling.tripRecyclerView.TripAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DriverTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView NUsername, Nemail;
    private String ProfilePicUrl,UserID, Date, Destination, Seats, Starting, LuggageCheck, Time;
    private FirebaseAuth mAuth;
    private DatabaseReference UserDb, reference;

    NavigationView navigationView;
    private ImageView navProfile;;

    private TabLayout tabLayout;
    private ViewPager tabSwitch;
    private TabAdapter tabAdapter;

    FirebaseUser CurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        UserID = mAuth.getCurrentUser().getUid();
        UserDb = FirebaseDatabase.getInstance().getReference().child("users").child(UserID);
        getUserDB();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
        NUsername = hView.findViewById(R.id.UsernameNav);
        Nemail = hView.findViewById(R.id.EmailNav);
        navProfile = hView.findViewById(R.id.imageView);

        setupFirebaseListener();

        tabLayout = findViewById(R.id.TabLayout);
        tabSwitch = findViewById(R.id.Switch);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        tabAdapter.addFragment(new PastTripFragment(),"Past");
        tabAdapter.addFragment(new presentTripsFragment(),"Today");
        tabAdapter.addFragment(new FutureTripFragment(),"Future");

        tabSwitch.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(tabSwitch);








        //tabSwitch.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //example of what will be put inside resultTrips..
       // Trip object = new Trip()

        //add
        //notify change



    }
    //get the unique keys of the trips under the user id



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {

            case R.id.action_settings:
                AlertDialog.Builder dialog = new AlertDialog.Builder(DriverTrips.this);
                dialog.setTitle("Are you sure you want to delete your account?");
                dialog.setMessage("By Doing this.....");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CurrentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //is deleted
                                    Toast.makeText(DriverTrips.this,"Account Successfully deleted",Toast.LENGTH_LONG).show();
                                    UserDb.removeValue();
                                    Intent intent = new Intent(DriverTrips.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(DriverTrips.this,"Account couldn't be deleted at this time",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_message:
                Intent msg = new Intent(DriverTrips.this, DriverMessage.class);
                startActivity(msg);
                break;

            case R.id.nav_profile:
                Intent profile = new Intent(DriverTrips.this, DriverProfile.class);
                startActivity(profile);
                break;

            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();

            case R.id.nav_create_trips:
                Intent create = new Intent(DriverTrips.this, DriverCreate.class);
                startActivity(create);
                break;

            case R.id.nav_my_trips:
                Intent trips = new Intent(DriverTrips.this, DriverTrips.class);
                startActivity(trips);
                break;

            case R.id.nav_find_trips_requests:
                Intent requests = new Intent(DriverTrips.this, DriverFindRequests.class);
                startActivity(requests);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    private void getUserDB(){
        UserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //makes sure the data is present, else the app will crash if not
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() >0){
                    //data originally added is kept in this format
                    Map<String,Object> map = (Map<String,Object>) dataSnapshot.getValue();
                    if(map.get("Username")!=null){
                        String DBUsername = map.get("Username").toString();
                        NUsername.setText(DBUsername);
                    }
                    if(map.get("profileImageUrl")!=null){
                        ProfilePicUrl = map.get("profileImageUrl").toString();
                        if(!ProfilePicUrl.equals("defaultPic")) {
                            Glide.with(getApplication()).load(ProfilePicUrl).into(navProfile);}
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //not needed
            }
        });
    }
    private void setupFirebaseListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String email = user.getEmail();
                    Nemail.setText(email);
                } else {
                    Toast.makeText(DriverTrips.this, "Sign Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DriverTrips.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }

    }
}
