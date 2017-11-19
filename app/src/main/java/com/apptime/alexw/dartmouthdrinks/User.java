package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
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
    private List<OnNight> history;

    public User (String userId, String name, int weight, boolean male){
        this.userId = userId;
        this.name = name;
        this.weight = weight;
        this.male = male;
        bac = 0.0;
        history = new ArrayList<>();
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
}
