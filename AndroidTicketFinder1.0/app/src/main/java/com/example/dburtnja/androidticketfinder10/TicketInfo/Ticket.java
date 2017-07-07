package com.example.dburtnja.androidticketfinder10.TicketInfo;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder10.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dburtnja on 04.07.17.
 * Object keep all info
 */

public class Ticket {
    private MainActivity    context;
    private RequestQueue    queue;
    private Station         stationFrom;
    private Station         stationTill;
    public TicketDate       dateFromStart;
    public TicketDate       dateFromEnd;
    private Train           train;
    private String          firstName;
    private String          lastName;

    public Ticket(MainActivity context, Train train) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.train = train;
    }

    public boolean setName(int firstName, int lastName){
        this.firstName = ((TextView)context.findViewById(firstName)).getText().toString();
        this.lastName = ((TextView)context.findViewById(lastName)).getText().toString();
        if (this.firstName == null || this.lastName == null){
            context.toast("Відсутнє ім'я чи прізвище", true);
            return false;
        }
        return true;
    }

    public Train getTrain() {
        return train;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public Station getStationTill() {
        return stationTill;
    }

    public void setStationFrom(EditText stationName) {
        this.stationFrom = new Station(stationName, queue, context);
    }

    public void setStationTill(EditText stationName) {
        this.stationTill = new Station(stationName, queue, context);
    }

    public void replaceStations(EditText sFrom, EditText sTill) {
        Station buf;

        buf = stationFrom;
        stationFrom = stationTill;
        stationTill = buf;
        sFrom.setText(stationFrom != null ? stationFrom.getTitle() : "");
        sTill.setText(stationTill != null ? stationTill.getTitle() : "");
    }

    public boolean checkIfAllSet(){
        if (stationFrom == null)
            return context.toast("Відсутня станція відправлення", true);
        else if (stationTill == null)
            return context.toast("Відсутня станція прибуття", true);
        else if (dateFromStart.getDate() == -1)
            return context.toast("Відсутній час відправлення", true);
        else if (dateFromEnd.getDate() == -1)
            return context.toast("Відсутній кінцевий час відправлення", true);
        else if (!train.coachIsSet(context))
            return false;
        else if (firstName == null)
            return context.toast("Відсутнє ім'я", true);
        else if (lastName == null)
            return context.toast("Відсутнє прізвище", true);
        return true;
    }

    public class Station {
        private String  title;
        private String  region;
        private int     value;

        public String getTitle() {
            return title;
        }

        public String getRegion() {
            return region;
        }

        public int getValue() {
            return value;
        }

        private Station(final EditText stationName, RequestQueue queue, final MainActivity activity){
            JsonArrayRequest    getRequest;
            String              url;

            url = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";
            try {
                url = url + URLEncoder.encode(String.valueOf(stationName.getText()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                activity.toast("Помилка кодування станції", true);
                e.printStackTrace();
            }
            getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray arrayResponse) {
                            JSONObject  response;

                            try {
                                response = arrayResponse.getJSONObject(0);
                                title = response.getString("title");
                                region = response.getString("region");
                                value = response.getInt("value");
                            } catch (JSONException e) {
                                activity.toast("Помилка отримання станції", true);
                                e.printStackTrace();
                            }
                            stationName.setText(title);
                            activity.toast("Станція: " + title + ". Значення: " + value, false);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            activity.toast("Помилка отримання даних станції", true);
                            Log.e("STATION_ERROR", error.getMessage());
                        }
                    });
            queue.add(getRequest);
        }
    }
}
