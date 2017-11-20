package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by briantomasco on 11/18/17.
 */

public class User {

    //user information

    private String userId;
    private String name;
    private int weight;
    private boolean male;
    private double bac;
    private Date timeOfLastCalc;
    private ArrayList<OnNight> history;
   // private String number;
    private Settings settings;
    private String friendNumber;

    public User (String userId, String name, int weight, boolean male, ArrayList<OnNight> list) {
        this.userId = userId;
        this.name = name;
        this.weight = weight;
        this.male = male;
        //this.number = number;
        timeOfLastCalc = new Date();
        settings = new Settings();
        friendNumber = "";
        bac = 0.0;
        history = list;
    }

    //blank constructor needed for firebase
    public User(){

    }


//add an on night to the history tab
    public void addOnNight(OnNight night) {
        history.add(night);
    }

    //getters and setters

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isMale() {
        return male;
    }

    public double getBac() {
        return bac;
    }

    public Date getTimeOfLastCalc() {
        return timeOfLastCalc;
    }

    public ArrayList<OnNight> getHistory() {
        return history;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public void setBac(double bac) {
        this.bac = bac;
    }

    public void setTimeOfLastCalc(Date timeOfLastCalc) {
        this.timeOfLastCalc = timeOfLastCalc;
    }

    public void setHistory(ArrayList<OnNight> history) {
        this.history = history;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getFriendNumber() {
        return friendNumber;
    }

    public void setFriendNumber(String friendNumber) {
        this.friendNumber = friendNumber;
    }
}
