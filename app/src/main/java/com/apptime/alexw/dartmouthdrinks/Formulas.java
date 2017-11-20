package com.apptime.alexw.dartmouthdrinks;

import android.util.Log;

/**
 * Created by briantomasco on 11/18/17.
 */

public class Formulas {

    public static double calculateBac (boolean male, int weight, double prevBac, double alcohol, double timeSinceCalc, double timeSinceDrink) {
        Log.d("TIME DIFF", String.valueOf(timeSinceCalc - timeSinceDrink));
        double changeInBac;

        if (male) changeInBac = (alcohol * Constants.WIDMARK_CONSTANT)/(weight * Constants.MALE_BAC_CONSTANT);
        else changeInBac = (alcohol * Constants.WIDMARK_CONSTANT)/(weight * Constants.FEMALE_BAC_CONSTANT);

        changeInBac = bacMetabolism(changeInBac, timeSinceDrink);

        if (changeInBac < 0) changeInBac = 0;

        prevBac = bacMetabolism(prevBac, timeSinceCalc);

        double currentBac = prevBac + changeInBac;

        return currentBac;
    }

    public static double bacMetabolism(double prevBac, double timeDiff) {
        return prevBac - (minToHours(timeDiff) * Constants.ALCOHOL_METABOLISM_RATE);
    }

    public static double drinkAlcoholContent(double amount, double percent){
        return amount * percent;
    }

    public static double minToHours(double minutes){
        return ((double)minutes)/60.0;
    }

    public static double milliToMinutes (long milliseconds) {
        return (double)(milliseconds / Constants.MILLISECONDS_PER_MINUTE);
    }
}
