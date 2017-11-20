package com.apptime.alexw.dartmouthdrinks;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by briantomasco on 11/18/17.
 */

public class Drink {

    private String name;
    private Date time;
    private double prevBac;
    private double amount;
    private double percent;
    private double alcohol;
    private double postBac;

    public Drink (String name, Date time, double bac, double amount, double percent){
        this.name = name;
        this.time = time;
        this.prevBac = bac;
        this.amount = amount;
        this.percent = percent;
        alcohol = Formulas.drinkAlcoholContent(amount, percent);
    }

    //Blank constructor for Firebase
    public Drink(){

    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }

    public double getPrevBac() {
        return prevBac;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercent() {
        return percent;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public double getPostBac() {
        return postBac;
    }

    public void setPostBac(double postBac) {
        this.postBac = postBac;
    }
}
