package com.apptime.alexw.dartmouthdrinks;

/**
 * Created by briantomasco on 11/18/17.
 */

public class Drink {

    private String name;
    private int time;
    private double prevBac;
    private double postBac;
    private double amount;
    private double percent;
    private double alcohol;

    public Drink (String name, int time, double bac, double amount, double percent){
        this.name = name;
        this.time = time;
        this.prevBac = bac;
        this.amount = amount;
        this.percent = percent;
        alcohol = Formulas.drinkAlcoholContent(amount, percent);
    }

    public void calcPostBac (User user, int lastCalcTime){
        //TODO: use instance variables to calculate bac after having drink

    }

    public String getName() {
        return name;
    }

    public int getTime() {
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
}
