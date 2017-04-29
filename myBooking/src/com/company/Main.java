package com.company;

import com.company.objects.Station;
import com.google.gson.Gson;

import java.net.URLEncoder;

public class Main {

    public static void main(String[] args) {
        Station from = new Station("Тернопіль");
        from.getStationId();
        System.out.println(from.label + " = " + from.value);
    }
}
