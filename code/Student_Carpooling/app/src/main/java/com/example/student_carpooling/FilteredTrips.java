package com.example.student_carpooling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.student_carpooling.findTripsRecyclerView.FindTrip;
import com.example.student_carpooling.findTripsRecyclerView.FindTripAdapter;
import com.example.student_carpooling.tripRecyclerView.TripAdapter;
import com.example.student_carpooling.usersRecyclerView.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

<<<<<<< HEAD
public class FilteredTrips extends AppCompatActivity {
=======
public class FilteredTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private RecyclerView tripRecyclerView;
    private RecyclerView.Adapter FiltertripAdapter;
    private RecyclerView.LayoutManager tripLayoutManager;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView NUsername, Nemail, txt;
    private String ProfilePicUrl,UserID, Date, Destination, Seats, Starting, LuggageCheck, First, Surname, Fullname, Note, Time, UserName,DriverProfilePicUrl;
    private FirebaseAuth mAuth;
    private String DBUsername;
    private DatabaseReference UserDb, reference;
    Date TripDate,date;
    LinearLayout linearLayout;
    private String DriverKey;
    FirebaseUser CurrentUser;


    Toolbar toolbar=null;


>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2



        private RecyclerView tripRecyclerView;
        private RecyclerView.Adapter FiltertripAdapter;
        private RecyclerView.LayoutManager tripLayoutManager;

        private FirebaseAuth.AuthStateListener mAuthStateListener;
        private TextView NUsername, Nemail, txt;
        private String ProfilePicUrl,UserID, Date, Destination, Seats, Starting, LuggageCheck, First, Surname, Fullname, Note, Time, UserName,DriverProfilePicUrl;
        private FirebaseAuth mAuth;
        private String DBUsername;
        private DatabaseReference UserDb, reference;
        Date TripDate,date;
        private String DriverKey;
        FirebaseUser CurrentUser;


        Toolbar toolbar;



        NavigationView navigationView;
        private ImageView navProfile;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_filtered_trips2);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Trip Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

<<<<<<< HEAD
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //be able to go back out of the activity
                    finish();
                }
            });

=======

        getDriverId();
        Toast.makeText(FilteredTrips.this, "Click on a trip to see more details, send request or message the driver ", Toast.LENGTH_LONG).show();
    }
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2

            mAuth = FirebaseAuth.getInstance();
            CurrentUser = mAuth.getCurrentUser();
            UserID = mAuth.getCurrentUser().getUid();
            UserDb = FirebaseDatabase.getInstance().getReference().child("users").child(UserID);

<<<<<<< HEAD
            tripRecyclerView = findViewById(R.id.FilterTripsRecycler);
            tripRecyclerView.setNestedScrollingEnabled(true); //not true?
            tripRecyclerView.setHasFixedSize(true);
            FiltertripAdapter = new FindTripAdapter(getDataFilterTrips(),FilteredTrips.this);
            tripLayoutManager = new LinearLayoutManager(FilteredTrips.this);
            tripRecyclerView.setLayoutManager(tripLayoutManager);

            tripRecyclerView.setAdapter(FiltertripAdapter);
=======
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2




            getDriverId();

            //not working yet..
            sortList();
            Toast.makeText(FilteredTrips.this, "Click on a trip to see more details, send request or message the driver ", Toast.LENGTH_LONG).show();
        }



<<<<<<< HEAD
    //first set up onclick for the button in the recycler view
=======
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

        switch (id){
            case R.id.pass_message:
                Intent msg = new Intent(FilteredTrips.this, PassengerMessage.class);
                startActivity(msg);
                break;

            case R.id.pass_profile:
                Intent profile = new Intent(FilteredTrips.this, PassengerProfile.class);
                startActivity(profile );
                break;

            case R.id.pass_sign_out:
                FirebaseAuth.getInstance().signOut();

            case R.id.pass_find_trips:
                Intent create = new Intent(FilteredTrips.this,FindTrips.class);
                startActivity(create);
                break;

            case R.id.pass_trips:
                Intent trips = new Intent(FilteredTrips.this,PassengerTrips.class);
                startActivity(trips);
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
                        DBUsername = map.get("Username").toString();
                        NUsername.setText(DBUsername);
                    }
                    if(map.get("profileImageUrl")!=null){
                        ProfilePicUrl = map.get("profileImageUrl").toString();

                        if(!ProfilePicUrl.equals("defaultPic")) {
                            Glide.with(getApplication()).load(ProfilePicUrl).into(navProfile);}
                    }
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            switch(item.getItemId()) {

                case R.id.action_settings:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FilteredTrips.this);
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
                                        Toast.makeText(FilteredTrips.this,"Account Successfully deleted",Toast.LENGTH_LONG).show();
                                        UserDb.removeValue();
                                        Intent intent = new Intent(FilteredTrips.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(FilteredTrips.this,"Account couldn't be deleted at this time",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    });

<<<<<<< HEAD
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


        private void getDriverId(){
            final DatabaseReference DriverID = FirebaseDatabase.getInstance().getReference().child("TripForms");
            DriverID.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot id : dataSnapshot.getChildren()) {
                            DriverKey = id.getKey();
                            getTripIds(DriverKey);
=======
    private void getDriverId(){
        final DatabaseReference DriverID = FirebaseDatabase.getInstance().getReference().child("TripForms");
        DriverID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot id : dataSnapshot.getChildren()) {
                        DriverKey = id.getKey();
                        getTripIds(DriverKey);
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private void getTripIds(final String Key){
            DatabaseReference TripIDs = FirebaseDatabase.getInstance().getReference().child("TripForms").child(Key);
            TripIDs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        //if there is any info there
                        for(DataSnapshot id : dataSnapshot.getChildren()){
                            //then get the info under that unique ID
                            String TripKey = id.getKey();
                            UserTripDB(Key, TripKey);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

<<<<<<< HEAD
                }
            });
        }


=======


    private void UserTripDB(String ID) {
        //push().getKey();

        //get all driver trips
        DatabaseReference TripsDB = FirebaseDatabase.getInstance().getReference().child("TripForms").child(DriverKey).child(ID);
        //Drivers full name is stored within "users"
        DatabaseReference UserDB = FirebaseDatabase.getInstance().getReference().child("users").child(DriverKey);
        UserDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Name") != null) {
                        String name = map.get("Name").toString();
                        First = name.substring(0, 1).toUpperCase() + name.substring(1);
                    }
                    if (map.get("Surname") != null) {
                        String surname = map.get("Surname").toString();
                        Surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
                    }

                    if(map.get("profileImageUrl")!= null){
                        DriverProfilePicUrl = map.get("profileImageUrl").toString();
                    }

                    if (map.get("Username") != null) {
                        UserName = map.get("Username").toString();
                    }

                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TripsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2

        private void UserTripDB(final String Key, String ID) {
            //push().getKey();

            //get all driver trips
            DatabaseReference TripsDB = FirebaseDatabase.getInstance().getReference().child("TripForms").child(Key).child(ID);
            //Drivers full name is stored within "users"
            DatabaseReference UserDB = FirebaseDatabase.getInstance().getReference().child("users").child(Key);
            UserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        if (map.get("Name") != null) {
                            String name = map.get("Name").toString();
                            First = name.substring(0, 1).toUpperCase() + name.substring(1);
                        }
                        if (map.get("Surname") != null) {
                            String surname = map.get("Surname").toString();
                            Surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
                        }

                        if(map.get("profileImageUrl")!= null){
                            DriverProfilePicUrl = map.get("profileImageUrl").toString();
                        }

                        if (map.get("Username") != null) {
                            UserName = map.get("Username").toString();
                        }

                    }

                }

<<<<<<< HEAD
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
=======
                    if (map.get("Luggage") != null) {
                        LuggageCheck = map.get("Luggage").toString().toUpperCase();
                    }
                    if (map.get("Note") != null) {
                        Note = map.get("Note").toString();
                    }
                    if (map.get("Starting")!= null) {
                        Starting = map.get("Starting").toString().toUpperCase();
                    }
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2

            TripsDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

<<<<<<< HEAD
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();


                        //check that none of them are null
                        if (map.get("Date") != null) {
                            Date = map.get("Date").toString();
                            StringTokenizer tokens = new StringTokenizer(Date, "/");
                            Integer day = Integer.parseInt(tokens.nextToken());
                            Integer month = Integer.parseInt(tokens.nextToken());
                            Integer year = Integer.parseInt(tokens.nextToken());
                            //year month date
                            // year in date is saying 3919 rather than 2019
=======
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2

                            Calendar calendar = Calendar.getInstance();

<<<<<<< HEAD
                            TripDate = new Date(year-1900, month - 1, day);
                            String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(TripDate);

                        }
                        if (map.get("Time") != null) {
                            Time = map.get("Time").toString();
                        }

                        if (map.get("Seats") != null) {
                            Seats = map.get("Seats").toString();
                        }

                        if (map.get("Luggage") != null) {
                            LuggageCheck = map.get("Luggage").toString().toUpperCase();
                        }
                        if (map.get("Note") != null) {
                            Note = map.get("Note").toString();
                        }
                        if (map.get("Starting")!= null) {
                            Starting = map.get("Starting").toString().toUpperCase();
                        }

                        if (map.get("Destination") != null) {
                            Destination = map.get("Destination").toString().toUpperCase();
                        }


                        String pattern = "dd-MM-yy";
                        date = new Date();
                        String Currentdate = new SimpleDateFormat(pattern).format(date);

                        if(!Key.equals(CurrentUser.getUid())) {
                            //make sure its not a past trips or isnt one that was created by that user.
                            int compare = date.compareTo(TripDate);
                            // if date greater, tripdate is a past d
                                 String[] timeSplit = Time.split(":");
                                 Integer hours = Integer.parseInt(timeSplit[0]);
                                 Integer mins = Integer.parseInt(timeSplit[1]);
                                 Integer totalMins = (hours * 60) + mins;


                                Fullname = First + " " + Surname;
                                FindTrip object = new FindTrip(Fullname,UserName,DriverProfilePicUrl,Time,Date,Starting,Destination,Seats,LuggageCheck,Note,Key);
                                results.add(object);
                                FiltertripAdapter.notifyDataSetChanged();
                            }
=======
                    if (!(UserName.equals(DBUsername))) {
                        //make sure its not a past trips or isnt one that was created by that user.
                        int compare = date.compareTo(TripDate);
                        // if date greater, tripdate is a past d
                        if (date.compareTo(TripDate) < 0 && !(date.compareTo(TripDate) > 0)) {

                            Fullname = First + " " + Surname;
                            FilterTrip object = new FilterTrip(DriverKey, Note, LuggageCheck, Fullname, DriverProfilePicUrl,Date, Time, Seats, UserName, Starting, Destination);
                            resultsTrips.add(object);
                            FiltertripAdapter.notifyDataSetChanged();
>>>>>>> a55d60a77aa0c4f8f9f61a406944db63df7d98b2
                        }

                    }




                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



        private ArrayList results = new ArrayList<FindTrip>();
        //private ArrayList resultsTrips;

        private ArrayList<FindTrip> getDataFilterTrips() {

            //isnt working
            // if(resultsTrips.size() == 0){
            //  txt.setText("There are no matching trips..");
            //}
            return results;
        }

            private void sortList(){

                Collections.sort(results, new Comparator<FindTrip>() {
                    @Override
                    public int compare(FindTrip o1, FindTrip o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });
                FiltertripAdapter.notifyDataSetChanged();

            }



        @Override
        public void onResume() {
            super.onResume();
            results.clear();

        }

    }
