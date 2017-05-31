package com.example.denys.androidticketfinder.Search.train_search;

/**
 * Created by Denys on 09.05.2017.
 */

import android.util.Log;

import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {

    public Object sendPost(String url, String urlParameters, Type type, Ticket ticket) {

    }
}

/*
*
*
* try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36" );

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.d("request to URL", url);
            Log.d("Post parameters : ", urlParameters);
            Log.d("Response Code : ", responseCode + "");

            for (int i = 0; i < con.getHeaderFields().size(); i++) {
                if (con.getHeaderField(i).contains("_gv_sessid")) {
                    ticket.cookie = con.getHeaderField(i);
                }
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            String str = in.readLine();
            if (type != null) {
                Gson gson = new Gson();
                try {
                    return gson.fromJson(str, type);
                } catch (Exception e) {
                    ticket.status = str;
                    return null;
                }
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            ticket.status = "Помилка методу POST за посиланням: " + url;
            return null;
        }
*
*
 */