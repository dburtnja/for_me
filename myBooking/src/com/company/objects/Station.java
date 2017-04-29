package com.company.objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Station {
    public String label;
    public int value;

    public Station(String label) {
        this.label = label;
    }

    public void getStationId() {
        Gson gson = new Gson();

        String url = "http://booking.uz.gov.ua/purchase/station/?term=" + URLEncoder.encode(this.label);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            Station[] stationsArray = gson.fromJson(in.readLine(), new TypeToken<Station[]>() {}.getType());
            this.value = stationsArray[0].value;
        } catch (Exception e) {
            System.out.println("Невірна назва санції");
        }

    }
}