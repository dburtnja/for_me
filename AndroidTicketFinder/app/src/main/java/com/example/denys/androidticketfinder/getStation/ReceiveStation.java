package com.example.denys.androidticketfinder.getStation;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.denys.androidticketfinder.Ticket.Ticket;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveStation {
    private final Vibrator vibrator;
    private Context context;
    private EditText stationName;

    public ReceiveStation(final EditText stationName, final Ticket.Station station, final Context context) {
        String url = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";
        RequestQueue queue = Volley.newRequestQueue(context);

        this.stationName = stationName;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.context = context;
        station.title = null;
        station.value = 0;
        try {
             url = url + URLEncoder.encode(String.valueOf(stationName.getText()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Response.Listener<JSONArray> onGetLoaded = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    station.value = jsonObject.getInt("value");
                    station.title = jsonObject.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (station.title != null && station.value != 0) {
                    stationName.setText(station.title);
                    Toast.makeText(context,
                            "Отримана станція " + station.title +
                            " із значенням " + station.value,
                            Toast.LENGTH_SHORT).show();
                } else
                    errorGet();
            }
        };

        Response.ErrorListener onGetError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GET_ERROR", error.getMessage());
                errorGet();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, onGetLoaded, onGetError);
        queue.add(jsonArrayRequest);
    }

    private void errorGet() {
        stationName.setText("");
        Toast.makeText(context, "Помилка отримання станції", Toast.LENGTH_LONG).show();
        vibrator.vibrate(500);
    }
}