package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by briantomasco on 11/19/17.
 */

public class OnNight {

    private List<Drink> drinkList;
    private long day;

    public OnNight(Date day) {
        this.day = day.getTime();
        drinkList = new ArrayList<>();
    }

    public OnNight(){

    }

    public void addDrink(Drink d) {
        drinkList.add(d);
    }

    public Date getDay() {
        return new Date (day);
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }
}
