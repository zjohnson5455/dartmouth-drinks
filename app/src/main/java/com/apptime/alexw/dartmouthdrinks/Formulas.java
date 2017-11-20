package com.apptime.alexw.dartmouthdrinks;

import android.util.Log;

/**
 * Created by briantomasco on 11/18/17.
 */

public class Formulas {

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

    public static double bacMetabolism(double prevBac, double timeDiff) {
        double newBac = prevBac - (minToHours(timeDiff) * Constants.ALCOHOL_METABOLISM_RATE);
        if (newBac <= 0) return 0.0;
        else return newBac;
    }

    public static double drinkAlcoholContent(double amount, double percent){
        return amount * percent;
    }

    public static double minToHours(double minutes){
        return ((double)minutes)/60.0;
    }

    public static double milliToMinutes (long milliseconds) {
        return ((double)milliseconds / (double)Constants.MILLISECONDS_PER_MINUTE);
    }

    public static long minutesToMilli (double minutes) {
        return (long)(minutes * Constants.MILLISECONDS_PER_MINUTE);
    }
}
