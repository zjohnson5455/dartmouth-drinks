package com.apptime.alexw.dartmouthdrinks;

import android.location.Criteria;
import android.util.Log;

/**
 * Created by briantomasco on 11/18/17.
 * BAC formulas
 */

public class Formulas {

    //uses Widmark formula to calculate BAC
    public static double calculateBac (User user, Drink drink, double timeSinceCalc, double timeSinceDrink) {
        double changeInBac;

        if (user.isMale()) changeInBac = (drink.getAlcohol() * Constants.WIDMARK_CONSTANT)/(user.getWeight() * Constants.MALE_BAC_CONSTANT);
        else changeInBac = (drink.getAlcohol() * Constants.WIDMARK_CONSTANT)/(user.getWeight() * Constants.FEMALE_BAC_CONSTANT);

        changeInBac = bacMetabolism(changeInBac, timeSinceDrink);

        if (changeInBac < 0) changeInBac = 0;

        double prevBac = bacMetabolism(drink.getPrevBac(), timeSinceCalc);

        double currentBac = prevBac + changeInBac;

        return currentBac;
    }

    //factors in metabolism to the calculation
    public static double bacMetabolism(double prevBac, double timeDiff) {
        double newBac = prevBac - (minToHours(timeDiff) * Constants.ALCOHOL_METABOLISM_RATE);
        if (newBac <= 0) return 0.0;
        else return newBac;
    }

    //factors in how alcoholic the drink is
    public static double drinkAlcoholContent(double amount, double percent){
        return amount * percent;
    }

    //converts minutes to hours
    public static double minToHours(double minutes){
        return ((double)minutes)/60.0;
    }

    //milliseconds to minutes
    public static double milliToMinutes (long milliseconds) {
        return ((double)milliseconds / (double)Constants.MILLISECONDS_PER_MINUTE);
    }

    //minutes to milliseconds
    public static long minutesToMilli (double minutes) {
        return (long)(minutes * Constants.MILLISECONDS_PER_MINUTE);
    }

    //set criteria for location
    protected static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        return criteria;
    }
}
