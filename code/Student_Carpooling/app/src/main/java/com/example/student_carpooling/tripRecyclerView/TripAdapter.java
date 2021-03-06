package com.example.student_carpooling.tripRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.student_carpooling.DriverTripItem;
import com.example.student_carpooling.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolders>{
    private List<Trip> list;
    private Context context;

    public TripAdapter(List<Trip> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_cards, viewGroup, false);
       // this forces it to match parent in width and wrap content in its height.
        RecyclerView.LayoutParams lp  = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new TripViewHolders(layoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolders tripViewHolders, int i) {
     //populating the cards
        tripViewHolders.Destination.setText(list.get(i).getDestination());
        //tripViewHolders.UserName.setText(list.get(i).getUserName());
        tripViewHolders.Starting.setText(list.get(i).getStarting());
        tripViewHolders.Time.setText(list.get(i).getTime());
        tripViewHolders.Date.setText(list.get(i).getDate());
        tripViewHolders.Seats.setText(list.get(i).getSeats());
        tripViewHolders.Luggage.setText(list.get(i).getLuggageCheck());

        final String tripid = list.get(i).getTripID();
        final String driverid = list.get(i).getDriverID();
        final String dst = list.get(i).getDestination();
        final String start = list.get(i).getStarting();
        final String time = list.get(i).getTime();
        final String date = list.get(i).getDate();
        final String seats = list.get(i).getSeats();
        final String luggage = list.get(i).getLuggageCheck();
        final String username = list.get(i).getUserName();
        final float lat = list.get(i).getDstLat();
        final float lon = list.get(i).getDstLon();


        tripViewHolders.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ""+list.get(i).getTripID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DriverTripItem.class);
                intent.putExtra("TripID", tripid);
                intent.putExtra("DriverID", driverid);
                intent.putExtra("Destination", dst);
                intent.putExtra("Starting", start);
                intent.putExtra("Time",time);
                intent.putExtra("Date", date);
                intent.putExtra("Seats", seats);
                intent.putExtra("Luggage",luggage);
                intent.putExtra("Username",username);
                intent.putExtra("DstLat",lat);
                intent.putExtra("DstLon",lon);
                context.startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
