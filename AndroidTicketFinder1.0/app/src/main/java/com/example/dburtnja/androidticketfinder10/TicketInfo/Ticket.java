package com.example.dburtnja.androidticketfinder10.TicketInfo;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dburtnja on 04.07.17.
 */

public class Ticket {
    private Context         context;
    private RequestQueue    queue;
    private Station         stationFrom;
    private Station         stationTill;

    public Ticket(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
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

    public void replaceStantions() {
        Station buf;

        buf = stationFrom;
        stationFrom = stationTill;
        stationTill = buf;
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

        private Station(final EditText stationName, RequestQueue queue, final Context context) {
            JsonArrayRequest    getRequest;
            String              url;

            url = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";
            try {
                url = url + URLEncoder.encode(String.valueOf(stationName.getText()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                toast("Помилка кодування станції", context, true);
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
                                toast("Помилка отримання станції", context, true);
                                e.printStackTrace();
                            }
                            stationName.setText(title);
                            toast("Станція: " + title + ". Значення: " + value, context, false);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            toast("Помилка отримання даних станції", context, true);
                            Log.e("STATION_ERROR", error.getMessage());
                        }
                    });
            queue.add(getRequest);
        }

        public void toast(String msg, Context context, Boolean vibrate) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            if (vibrate) {
                Vibrator    vibrator;

                vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);;
                vibrator.vibrate(500);
            }
        }
    }
}
