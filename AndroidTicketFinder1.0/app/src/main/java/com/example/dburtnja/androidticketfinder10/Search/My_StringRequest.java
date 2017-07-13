package com.example.dburtnja.androidticketfinder10.Search;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dburtnja on 13.07.17.
 */

public class My_StringRequest extends StringRequest {
    private Ticket                  ticket;
    private Map<String, String>     param;

    public My_StringRequest(final String url, Response.Listener<String> listener, final Ticket ticket, Map<String, String> param) {
        super(Method.POST, url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ticket.setError("Помилка: " + url);
            }
        });
        this.ticket = ticket;
        this.param = param;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;

        headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.param;
    }
}
