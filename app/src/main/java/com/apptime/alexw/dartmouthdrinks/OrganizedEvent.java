package com.apptime.alexw.dartmouthdrinks;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by briantomasco on 11/20/17.
 */

public class OrganizedEvent {

    private String name;
    private LatLng position;
    private double radius;
    private double bacThreshold;
    private String contactNumber;
    private boolean active;
    private Date startTime;
    private Date endTime;

    public OrganizedEvent(String name, double lat, double lng, double rad, double bac, String lst) {
        this.name = name;
        position = new LatLng(lat, lng);
        radius = rad;
        bacThreshold = bac;
        contactNumber = lst;
        active = false;
        startTime = null;
        endTime = null;
    }

    public boolean isInFence(LatLng pos) {
        float[] results = new float[1];
        Location.distanceBetween(pos.latitude, pos.longitude, position.latitude, position.longitude, results);
        float diffDist = results[0];
        return diffDist <= radius;
    }

    public String testNotify(double bac, LatLng pos){
        if (bac > bacThreshold && isInFence(pos)) return contactNumber;
        else return null;
    }

    public String getContactNumbers(){
        return contactNumber;
    }

    public void setContactNumbers(String number) {
        contactNumber = number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
