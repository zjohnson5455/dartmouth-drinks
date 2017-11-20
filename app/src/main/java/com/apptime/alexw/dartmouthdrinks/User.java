package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by briantomasco on 11/18/17.
 */

public class User {

    private String userId;
    private String name;
    private int weight;
    private boolean male;
    private double bac;
    private Date timeOfLastCalc;
    private List<OnNight> history;

    public User (String userId, String name, int weight, boolean male){
        this.userId = userId;
        this.name = name;
        this.weight = weight;
        this.male = male;
        timeOfLastCalc = new Date();

        bac = 0.0;
        history = new ArrayList<>();
    }

    public User(){

    }

    public void addOnNight(OnNight night) {
        history.add(night);
    }

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
}
