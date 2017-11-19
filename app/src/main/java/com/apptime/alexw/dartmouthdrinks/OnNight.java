package com.apptime.alexw.dartmouthdrinks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by briantomasco on 11/19/17.
 */

public class OnNight {

    private List<Drink> drinkList;
    private Calendar day;

    public OnNight(Calendar day) {
        this.day = day;
        drinkList = new ArrayList<>();
    }

    public void addDrink(Drink d) {
        drinkList.add(d);
    }

}
