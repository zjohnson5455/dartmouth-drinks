package com.apptime.alexw.dartmouthdrinks;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorString;

/**
 * Created by zacharyjohnson on 11/20/17.
 * Used tutorial from https://code.tutsplus.com/tutorials/how-to-work-with-geofences-on-android--cms-26639
 */

public class GeofenceTransitionService extends IntentService {

    private static final String TAG = GeofenceTransitionService.class.getSimpleName();

    public GeofenceTransitionService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("FENCE", "Hi from TransitionService");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);


        // Handling errors
        if ( geofencingEvent.hasError() ) {
            Log.d( "TRANSITION", "Error in Geofence Transitioning" );
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        Log.d("FENCE", String.valueOf(geoFenceTransition));
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            //get triggered geofence
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            //create message with those received


        }

        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(getApplicationContext(), "You ENTERED YAAAAAAYYYY", Toast.LENGTH_SHORT).show();
        }

    }


}
