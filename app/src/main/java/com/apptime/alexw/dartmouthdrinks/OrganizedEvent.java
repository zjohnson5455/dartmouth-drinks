package com.apptime.alexw.dartmouthdrinks;

import android.location.Location;

import java.util.Date;

/**
 * Created by Alex W on 20/11/2017.
 */
import com.google.android.gms.maps.model.LatLng;

public class OrganizedEvent {

    private String name;
    Double latitude;
    Double longitude;
    private double radius;
    private double bacThreshold;
    private String contactNumber;


    //set up an event that can have a geofence around it

    public OrganizedEvent(String name, double lat, double lng, double rad, double bac, String lst) {
        this.name = name;
        latitude = lat;
        longitude = lng;
        radius = rad;
        bacThreshold = bac;
        contactNumber = lst;

    }

    //need blank constructor for firebase
    public OrganizedEvent(){

    }

    //check if location is within the fence
    public boolean isInFence(double lat, double lng) {
        float[] results = new float[1];
        Location.distanceBetween(lat, lng, latitude, longitude, results);
        float diffDist = results[0];
        return diffDist <= radius;
    }

    //checks if conditions for notification apply and then returns number to which to send the message
    public String testNotify(double bac, double lat, double lng){
        if (bac > bacThreshold && isInFence(lat, lng)) return contactNumber;
        else return null;
    }

    //getters and setters
    public String getContactNumbers(){
        return contactNumber;
    }

    public void setContactNumbers(String number) {
        contactNumber = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getBacThreshold() {
        return bacThreshold;
    }

    public void setBacThreshold(double bacThreshold) {
        this.bacThreshold = bacThreshold;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}