package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by briantomasco on 11/19/17.
 */

public class OnNight {

    //tracks the activity for a given night

    private List<Drink> drinkList;
    private Date day;

    public OnNight(Date day) {
        this.day = day;
        drinkList = new ArrayList<>();
    }

    public OnNight(){

    }

    //adds a drink to the total for the night
    public void addDrink(Drink d) {
        drinkList.add(d);
    }

    //getters and setters
    public Date getDay() {
        return day;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }
}
