package com.example.student_carpooling.tripRecyclerView;

public class Trip {

    private String TripID;
    private String Date;
    private String Time;
    private String UserName;
    private String Seats;
    private String LuggageCheck;
    private String Starting;
    private String Destination;
    private String DriverID;
    private float DstLon;
    private float DstLat;



    public Trip(float DstLat, float DstLon, String UserName, String TripID,String DriverID,String Date,String Time,String Seats,String LuggageCheck, String Starting, String Destination ){
        this.Date = Date;
        this.Time = Time;
        this.UserName = UserName;
        this.Seats = Seats;
        this.LuggageCheck = LuggageCheck;
        this.Starting = Starting;
        this.Destination = Destination;
        this.TripID = TripID;
        this.DriverID = DriverID;
        this.DstLat = DstLat;
        this.DstLon = DstLon;

    }

    //may not need the set functions


    public float getDstLat() {
        return DstLat;
    }

    public void setDstLat(float dstLat) {
        DstLat = dstLat;
    }

    public float getDstLon() {
        return DstLon;
    }

    public void setDstLon(float dstLon) {
        DstLon = dstLon;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public String getTripID() {
        return TripID;
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName(){
        return UserName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        this.Seats = seats;
    }

    public void setLuggageCheck(String luggageCheck) {
        this.LuggageCheck = luggageCheck;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public void setStarting(String starting) {
        this.Starting = starting;
    }

    public String getStarting(){
        return Starting;
    }

    //public void setUserName(String userName) {
        //UserName = userName;
    //}

    public String getDestination() {
        return Destination;
    }

    public String getTime(){
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    //public String getUserName(){
        //return UserName;
    //}

    public String getLuggageCheck() {
        return LuggageCheck;
    }
}
