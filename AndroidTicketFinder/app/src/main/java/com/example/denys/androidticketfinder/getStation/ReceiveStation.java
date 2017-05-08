package com.example.denys.androidticketfinder.getStation;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denys.androidticketfinder.Ticket.Ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveStation {
    private Station station;
    private TextView status;
    private EditText stationName;

    public ReceiveStation(EditText stationName, Station station, TextView status) {
        this.station = station;
        this.status = status;
        this.stationName = stationName;
        try {
            new JSONTask().execute("http://booking.uz.gov.ua/mobile/train_search/station/?term=" + URLEncoder.encode(String.valueOf(stationName.getText()), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public class JSONTask extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONArray array = new JSONArray(finalJson);
                JSONObject station = array.getJSONObject(0);

                return station;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result == null) {
                status.setText("Помилка пошуку станції");
                stationName.setText("");
            }
            else {
                try {
                    station.value = result.getInt("value");
                    station.label = result.getString("label");
                    stationName.setText(result.getString("label"));
                } catch (JSONException e) {
                    status.setText("Помилка пошуку станції");
                    e.printStackTrace();
                }
            }
        }
    }
}