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
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ticket.status = error.getMessage();
                ticket.error = true;
                Log.e("VOLLEY_ERROR", error.getMessage());
            }
        };
    }

    public void findTicket(){
        StringRequest   request;
        String          url;

        url = "http://booking.uz.gov.ua/mobile/train_search/";
        request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String      jsonStr;

                        try {
                            jsonStr = new String(response.getBytes(), "UTF-8");
                            ticket.setJsonObj(jsonStr, "trainList");
                        } catch (UnsupportedEncodingException e) {
                            ticket.error = true;
                            ticket.status = "Кодування не підтримується";
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return ticket.getSearchParam();
            }
        };
        queue.add(request);
    }

    private void findPlace(){

    }
}
