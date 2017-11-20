package com.apptime.alexw.dartmouthdrinks;

/**
 * Created by Alex W on 20/11/2017.
 */

public class Settings {

    //gets the settings and controls them

    Boolean me;
    Boolean friends;
    Boolean organizer;
    private Double threshhold;

    public Settings() {
        me = true;
        friends = false;
        organizer = false;
        threshhold = 0.2;
    }

    //getters and setters

    public Boolean getMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }

    public Boolean getFriends() {
        return friends;
    }

    public void setFriends(Boolean friends) {
        this.friends = friends;
    }

    public Boolean getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Boolean organizer) {
        this.organizer = organizer;
    }

    public Double getThreshhold() {
        return threshhold;
    }

    public void setThreshhold(Double threshhold) {
        this.threshhold = threshhold;
    }
}
