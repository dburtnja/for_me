package com.example.denys.androidticketfinder.Ticket;


import com.example.denys.androidticketfinder.getStation.Station;


/**
 * Created by Denys on 07.05.2017.
 */

public class Ticket {
    public boolean sound;
    public int seekBarVal;
    public Station fromStation = new Station();
    public Station tillStation = new Station();
    public long fromDate;
    public long tillDate;
}
