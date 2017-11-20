package com.apptime.alexw.dartmouthdrinks;

import android.content.Intent;
import android.util.Log;

/**
 * Created by briantomasco on 11/18/17.
 */

public class Formulas {

    public static double calculateBac (boolean male, int weight, double prevBac, double alcohol, int timeSinceCalc, int timeSinceDrink) {
        double bacAtDrink = bacMetabolism(prevBac, timeSinceCalc - timeSinceDrink);
        double changeInBac;

        if (male) changeInBac = (alcohol * Constants.WIDMARK_CONSTANT)/(weight * Constants.MALE_BAC_CONSTANT);
        else changeInBac = (alcohol * Constants.WIDMARK_CONSTANT)/(weight * Constants.FEMALE_BAC_CONSTANT);

        Log.d("BAC", Double.toString(changeInBac));

        double bacAfterDrink = bacAtDrink + changeInBac;
        double currentBac = bacMetabolism(bacAfterDrink, timeSinceDrink);

        return currentBac;
    }

    public static double bacMetabolism (double prevBac, int timeDiff) {
        return prevBac - (minToHours(timeDiff) * Constants.ALCOHOL_METABOLISM_RATE);
    }

    public static double drinkAlcoholContent (double amount, double percent){
        return amount * percent;
    }

    public static double lbsToGrams (int weight){
        return weight * Constants.GRAMS_PER_POUND;
    }

    public static double flOzToGrams (double amount){
        return amount * Constants.GRAMS_PER_FL_OZ;
    }

    public static double minToHours (int minutes){
        return ((double)minutes)/60.0;
    }
}
