package com.example.denys.androidticketfinder.Ticket;



import com.example.denys.androidticketfinder.Search.train_search.coach.Coach;
import com.example.denys.androidticketfinder.Search.train_search.coaches.TestClass;


public class Ticket {
    public int errorCounter;
    public int seekBarVal;
    public Station fromStation = new Station();
    public Station tillStation = new Station();
    public long fromDate;
    public long mainFromDate;
    public long tillDate;
    public String firstName;
    public String lastName;
    public Place place;
    public String place_nbr;
    public TestClass coaches;
    public String cookie;
    public String train_nbr;
    public String coach_type;
    public int coach_type_id;
    public int coach_num;
    public String coach_class;
    public Coach coach;
    public String status;
    public boolean switch1;


    public class Station {
        public String title;
        public Integer value;
    }
}
