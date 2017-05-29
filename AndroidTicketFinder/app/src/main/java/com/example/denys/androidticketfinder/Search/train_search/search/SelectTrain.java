package com.example.denys.androidticketfinder.Search.train_search.search;


import com.example.denys.androidticketfinder.Search.train_search.Post;
import com.example.denys.androidticketfinder.Search.train_search.coaches.SendCoaches;
import com.example.denys.androidticketfinder.Ticket.Ticket;

/**
 * Created by Denys on 15.05.2017.
 */

public class SelectTrain {

    public boolean findPlace(TrainSearch trains, Ticket ticket, Post post) {
        long trainData;
        int myData;

        for (int i = 0; i < trains.getValue().size(); i++) {
            trainData = trains.getValue().get(i).getFrom().getDate().longValue();
            myData = (int) (ticket.tillDate / 1000);
            if (myData >= trainData) {
                for (int j = 0; j < trains.getValue().get(i).getTypes().size(); j++) {
                    if (ticket.place.isSuitable(trains.getValue().get(i).getTypes().get(j).getId(), ticket)) {
                        ticket.train_nbr = trains.getValue().get(i).getNum();
                        SendCoaches sendCoaches = new SendCoaches();
                        if (sendCoaches.SendCoachesFunc(ticket, post)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}