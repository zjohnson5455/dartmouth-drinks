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



    public OrganizedEvent(String name, double lat, double lng, double rad, double bac, String lst) {
        this.name = name;
        latitude = lat;
        longitude = lng;
        radius = rad;
        bacThreshold = bac;
        contactNumber = lst;

    }

    public OrganizedEvent(){

    }

    public boolean isInFence(double lat, double lng) {
        float[] results = new float[1];
        Location.distanceBetween(lat, lng, latitude, longitude, results);
        float diffDist = results[0];
        return diffDist <= radius;
    }

    public String testNotify(double bac, double lat, double lng){
        if (bac > bacThreshold && isInFence(lat, lng)) return contactNumber;
        else return null;
    }

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