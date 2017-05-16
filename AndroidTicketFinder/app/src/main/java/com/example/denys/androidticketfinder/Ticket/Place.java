package com.example.denys.androidticketfinder.Ticket;

import android.widget.CheckBox;

/**
 * Created by Denys on 15.05.2017.
 */

public class Place {

    private boolean kPlace = false;
    private boolean pPlace = false;
    private boolean c1Place = false;
    private boolean c2Place = false;

    public Place(CheckBox eny, CheckBox kPlace, CheckBox pPlace, CheckBox c1Place, CheckBox c2Place) {
        if (eny.isChecked()) {
            this.kPlace = true;
            this.pPlace = true;
            this.c1Place = true;
            this.c2Place = true;
        } else {
            if (kPlace.isChecked())
                this.kPlace = true;
            if (pPlace.isChecked())
                this.pPlace = true;
            if (c1Place.isChecked())
                this.c1Place = true;
            if (c2Place.isChecked())
                this.c2Place = true;
        }
    }

    public boolean isSuitable(String id, Ticket ticket) {
        if (this.pPlace && id.matches("П")) {
            ticket.coach_type = "П";
            return true;
        } else if (this.kPlace && id.matches("К")) {
            ticket.coach_type = "К";
            return true;
        } else if (this.c1Place && id.matches("С1")) {
            ticket.coach_type = "С1";
            return true;
        } else if (this.c2Place && id.matches("С2")) {
            ticket.coach_type = "С2";
            return true;
        }
        return false;
    }
}
