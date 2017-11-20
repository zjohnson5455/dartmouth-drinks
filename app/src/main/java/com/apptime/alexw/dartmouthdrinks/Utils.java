package com.apptime.alexw.dartmouthdrinks;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Alex W on 19/11/2017.
 */

public class Utils {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
