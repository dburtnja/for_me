package com.example.denys.androidticketfinder.Search.train_search.add;

import android.util.Log;

import com.example.denys.androidticketfinder.Search.train_search.Post;
import com.example.denys.androidticketfinder.Ticket.Ticket;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add {

    public boolean sendAdd(Ticket ticket, Post post) {
        String testStr;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date depDate = new Date(ticket.fromDate);
        try {
            String param = "from=" + ticket.fromStation.value +
                    "&to=" + ticket.tillStation.value +
                    "&train=" + URLEncoder.encode(ticket.train_nbr, "UTF-8") +
                    "&date=" + format.format(depDate) +
                    "&round_trip=0" +
                    "&places[0][ord]=0" +
                    "&places[0][charline]=" +
                    "&places[0][wagon_num]=" + ticket.coach_num +
                    "&places[0][wagon_class]=" + URLEncoder.encode(ticket.coach_class, "UTF-8") +
                    "&places[0][wagon_type]=" + URLEncoder.encode(ticket.coach_type, "UTF-8") +
                    "&places[0][firstname]=" + URLEncoder.encode(ticket.firstName, "UTF-8") +
                    "&places[0][lastname]=" + URLEncoder.encode(ticket.lastName, "UTF-8") +
                    "&places[0][bedding]=0" +
                    "&places[0][child]=" +
                    "&places[0][stud]=" +
                    "&places[0][transportation]=0" +
                    "&places[0][reserve]=0" +
                    "&places[0][place_num]=" + ticket.place_nbr;
            testStr = (String) post.sendPost("http://booking.uz.gov.ua/cart/add/", param, null, ticket);
            return testStr.contains("\"error\":false,\"data\":null,\"captcha\":null");  //перевірити чи не було помилки
        } catch (Exception e) {
            Log.d("Error", "Add send error");
        }
        return false;
    }
}
