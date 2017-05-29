package com.example.denys.androidticketfinder.Search.train_search.coaches;

import android.util.Log;

import com.example.denys.androidticketfinder.Search.train_search.Post;
import com.example.denys.androidticketfinder.Search.train_search.add.Add;
import com.example.denys.androidticketfinder.Search.train_search.coach.Coach;
import com.example.denys.androidticketfinder.Ticket.Ticket;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class SendCoaches {

    private void selectCoach(Ticket ticket) {
        ticket.coach_num = ticket.coaches.getCoaches().get(0).getNum();
        ticket.coach_class = ticket.coaches.getCoaches().get(0).getCoachClass();
        ticket.coach_type_id = ticket.coaches.getCoaches().get(0).getCoachTypeId();
    }

    private void findPlace(Ticket ticket) {
        List<String> places = ticket.coach.getValue().getPlaces().getValue();
        for (String place : places) {
            if (Integer.parseInt(place) % 2 == 1) {
                ticket.place_nbr = place;
                break;
            }
        }
        if (ticket.place_nbr == null)
            ticket.place_nbr = places.get(0);
    }

    public boolean SendCoachesFunc(Ticket ticket, Post post) {
        String coachesParam;
        try {
            coachesParam = "station_id_from=" + ticket.fromStation.value +
                    "&station_id_till=" + ticket.tillStation.value +
                    "&train=" + URLEncoder.encode(ticket.train_nbr, "UTF-8") +
                    "&coach_type=" + URLEncoder.encode(ticket.coach_type, "UTF-8") +
                    "&model=0" +
                    "&date_dep=" + (int)(ticket.tillDate / 1000) +
                    "&round_trip=0" +
                    "&another_ec=0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        Object obj = post.sendPost("http://booking.uz.gov.ua/purchase/coaches/", coachesParam, TestClass.class, ticket);
        if (obj.getClass() == String.class) {
            Log.d("purchase/coaches/", (String) obj);
            return false;
        } else {
            ticket.coaches = (TestClass) obj;
        }
        selectCoach(ticket);
        String coachParam;
        try {
            coachParam = "station_id_from=" + ticket.fromStation.value +
                    "&station_id_till=" + ticket.tillStation.value +
                    "&train=" + URLEncoder.encode(ticket.train_nbr, "UTF-8") +
                    "&coach_num=" + ticket.coach_num +
                    "&coach_class=" + URLEncoder.encode(ticket.coach_class, "UTF-8") +
                    "&coach_type_id=" + ticket.coach_type_id +
                    "&date_dep=" + (int)(ticket.tillDate / 1000) +
                    "&scheme_id=0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("coachParam = " + coachParam);
        Object newCoach =  post.sendPost("http://booking.uz.gov.ua/purchase/coach/", coachParam, Coach.class, ticket);
        try {
            ticket.coach = (Coach) newCoach;
            findPlace(ticket);
            if (ticket.firstName != null) {
                new Add().sendAdd(ticket, post);
            } else
                ticket.cookie = null;
            return true;
        } catch (Exception e) {
            Log.d("Error", "Помилка SendCoaches:46");
            return false;
        }
    }
}
