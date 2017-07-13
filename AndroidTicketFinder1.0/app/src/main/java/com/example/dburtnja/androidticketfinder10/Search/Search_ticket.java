package com.example.dburtnja.androidticketfinder10.Search;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by dburtnja on 12.07.17.
 * Search class
 */

public class Search_ticket {
    private final static long           DAY = 86400000;
    private Ticket                      ticket;
    private Context                     service;
    private RequestQueue                queue;
    public Response.ErrorListener       errorListener;

    public Search_ticket(final Ticket ticket, Context service) {
        this.ticket = ticket;
        this.service = service;
        queue = Volley.newRequestQueue(service);
    }

    public void checkForTrain(){
        long    nextDay;

        nextDay = ticket.dateFromStart.getDate();
        while (nextDay < ticket.dateFromEnd.getDate()){

        }
    }

    public void findTicket(){
        StringRequest   request;
        String          url;

        url = "http://booking.uz.gov.ua/purchase/search/";
        request = new My_StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  trainList;

                if ((trainList = responseToJson(response)) != null)
                    findPlace(trainList);
            }
        }, ticket, ticket.getSearchParam());
        queue.add(request);
    }

    private JSONObject responseToJson(String response){
        String      jsonStr;
        JSONObject  jsonObj;

        try {
            jsonStr = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            Log.d("response:77", jsonStr);
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.getString("error").equals("true")) {
                ticket.setError(jsonObj.getString("value"));
                return null;
            }
            return jsonObj;
        } catch (UnsupportedEncodingException e) {
            ticket.setError("Кодування не підтримується");
            e.printStackTrace();
        } catch (JSONException e) {
            ticket.setError("Помилка створення JSON об'єкту");
            e.printStackTrace();
        }
        return null;
    }

    private void findPlace(JSONObject trainList){
        try {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
