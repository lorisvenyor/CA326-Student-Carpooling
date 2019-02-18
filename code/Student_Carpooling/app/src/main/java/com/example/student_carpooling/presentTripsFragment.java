package com.example.student_carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_carpooling.tripRecyclerView.Trip;
import com.example.student_carpooling.tripRecyclerView.TripAdapter;
import com.example.student_carpooling.tripRecyclerView.TripViewHolders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;


public class presentTripsFragment extends Fragment  {

    private RecyclerView tripRecyclerView;
    private TripAdapter tripAdapter;
    private RecyclerView.LayoutManager tripLayoutManager;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView NUsername, Nemail;
    private String ProfilePicUrl,UserID, Date, Destination, Seats, Starting, LuggageCheck, Time,UserName;
    private FirebaseAuth mAuth;
    private DatabaseReference UserDb, reference;
    Date TripDate,date;
    LinearLayout linearLayout;

    TextView testing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_present_trips, container, false);
        tripRecyclerView = v.findViewById(R.id.trippresentRecycler);
        tripRecyclerView.setNestedScrollingEnabled(false); //not true?
        tripRecyclerView.setHasFixedSize(true);
        tripAdapter = new TripAdapter(getDataTrips(),getActivity());
        tripLayoutManager = new LinearLayoutManager(getActivity());
        tripRecyclerView.setLayoutManager(tripLayoutManager);

        tripRecyclerView.setAdapter(tripAdapter);

        tripAdapter.setTripListener(new TripAdapter.onTripListener() {
            @Override
            public void onTripClick(int position) {
                Intent intent = new Intent(getActivity(),DriverTripItem.class);
                startActivity(intent);
            }
        });

        //toolbar


        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();



        getTripIds();

        return v;
    }


    private void getTripIds(){
        DatabaseReference TripIDs = FirebaseDatabase.getInstance().getReference().child("TripForms").child(UserID);
        TripIDs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //if there is any info there
                    for(DataSnapshot id : dataSnapshot.getChildren()){
                        //then get the info under that unique ID
                        String key = id.getKey();
                        UserTripDB(key);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void UserTripDB(String ID) {
        //push().getKey();
        DatabaseReference TripsDB = FirebaseDatabase.getInstance().getReference().child("TripForms").child(UserID).child(ID);
        TripsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    Map<String,Object> map = (Map<String,Object>) dataSnapshot.getValue();


                    //check that none of them are null
                    if(map.get("Date")!=null){
                        Date = map.get("Date").toString();
                        StringTokenizer tokens = new StringTokenizer(Date, "/");
                        Integer day = Integer.parseInt(tokens.nextToken());
                        Integer month = Integer.parseInt(tokens.nextToken());
                        Integer year = Integer.parseInt(tokens.nextToken());


                        TripDate = new Date(year-1900,month-1,day);
                        String pattern = "dd-MM-YYYY";
                        String date_n =new SimpleDateFormat("dd-MM-yy",Locale.ENGLISH).format(TripDate);

                    }
                    if(map.get("Time")!=null){
                        Time = map.get("Time").toString();}

                    if(map.get("Seats")!=null){
                        Seats = map.get("Seats").toString();}

                    if(map.get("Luggage")!=null){
                        LuggageCheck = map.get("Luggage").toString().toUpperCase();
                    }

                    if(map.get("Starting")!=null){
                        Starting = map.get("Starting").toString().toUpperCase();
                    }

                    if(map.get("Destination")!=null){
                        Destination = map.get("Destination").toString().toUpperCase();}

                    if(map.get("Username")!=null){
                        UserName = map.get("Username").toString();}

                        //do the same with profile image

                    Integer CurrentDay;
                    Integer CurrentMonth;
                    Integer CurrentYear;
                    Integer CurrentHour;
                    Integer CurrentMins;

                    // split the Trips Date and compare against current



                    String pattern = "dd-MM-yy";
                    date = new Date();
                    String date_n =new SimpleDateFormat(pattern).format(date);
                    //17-02-2019



                    if(TripDate.equals(date)){
                        // this means its a past date...
                        Trip object = new Trip(Date,Time,Seats,LuggageCheck,Starting,Destination);
                        resultsTrips.add(object);
                        tripAdapter.notifyDataSetChanged();}



               }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList resultsTrips = new ArrayList<Trip>();

    private ArrayList<Trip> getDataTrips() {


        return resultsTrips;

    }

    @Override
    public void onResume() {
        super.onResume();
        resultsTrips.clear();

    }
}
