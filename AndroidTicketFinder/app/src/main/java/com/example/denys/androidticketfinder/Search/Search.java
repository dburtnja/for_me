package com.example.denys.androidticketfinder.Search;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.denys.androidticketfinder.Main2Activity;
import com.example.denys.androidticketfinder.MainActivity;
import com.example.denys.androidticketfinder.Search.train_search.Post;
import com.example.denys.androidticketfinder.Search.train_search.search.SelectTrain;
import com.example.denys.androidticketfinder.Search.train_search.search.TrainSearch;
import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.google.gson.Gson;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Search{
    private static long         DAY = 86400000;
    private Context             context;
    private Ticket              ticket;
    private SimpleDateFormat    simpleDate;
    private SimpleDateFormat    simpleTime;
    private RequestQueue        queue;

    public Search(Context context, Ticket ticket) {
        this.context = context;
        this.ticket = ticket;
        this.simpleDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        this.simpleTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.queue = Volley.newRequestQueue(context);
    }

    public void findTicket() {
        long        nextDayTime;

        nextDayTime = ticket.mainFromDate;
        while (nextDayTime < ticket.tillDate) {
            ticket.fromDate = nextDayTime;
            checkForTrain(ticket);
            nextDayTime = getNextDayTime(nextDayTime);
        }
    }

    private void checkForTrain(Ticket ticket) {
        String          searchParam;
        String          url;
        SelectTrain     selectTrain;
        StringRequest   postRequest;

        url = "http://booking.uz.gov.ua/purchase/search/";
        postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SEARCH.Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SEARCH.Error", error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params;
                Date                fromDate;
                Date                tillDate;
                Ticket              ticket;

                ticket = Search.this.ticket;
                params = new HashMap<>();
                fromDate = new Date(ticket.fromDate);
                tillDate = new Date(ticket.tillDate);
                params.put("station_id_from", ticket.fromStation.value + "");
                params.put("station_id_till", ticket.tillStation.value + "");
                params.put("station_from", "");
                params.put("station_till", "");
                params.put("date_dep", simpleDate.format(fromDate));
                params.put("time_dep", simpleTime.format(fromDate));
                params.put("time_dep_till", simpleTime.format(tillDate));
                params.put("another_ec", "0");
                params.put("search", "");
                return params;
            }
        };
        queue.add(postRequest);

        /*
        Object obj = post.sendPost("http://booking.uz.gov.ua/purchase/search/", searchParam,
                TrainSearch.class, ticket);
        if (obj != null) {
            selectTrain = new SelectTrain();
            if (selectTrain.findPlace((TrainSearch)obj, ticket, post)) {

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                MediaPlayer player = MediaPlayer.create(context, notification);
                player.setLooping(true);
                player.start();
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("_gv_sessid", ticket.cookie);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
            else
                onCheckNotification();
        }*/
    }

    private long getNextDayTime(long inputTime) {
        Date    nextDayDate;
        Date    plusDay;

        nextDayDate = null;
        plusDay = new Date(inputTime + DAY);
        try {
            nextDayDate = simpleDate.parse(simpleDate.format(plusDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nextDayDate == null)
            return (-1);
        else
            return (nextDayDate.getTime());
    }

    private void onCheckNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentTitle("Перевірив" + ticket.fromStation.title + " => " + ticket.tillStation.title)
                .setContentText(ticket.status)
                .setContentInfo("INfo");

        NotificationManager nM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nM.notify(1, builder.build());
    }
}

/*

if (obj != null) {
            SelectTrain selectTrain = new SelectTrain();
            if (selectTrain.findPlace((TrainSearch)obj, ticket, post)) {

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                MediaPlayer player = MediaPlayer.create(context, notification);
                player.setLooping(true);
                player.start();
                Search.this.timer.cancel();
                Intent intent1 = new Intent(Search.this, Main2Activity.class);
                intent1.putExtra("_gv_sessid", ticket.cookie);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
                    else
                    onCheckNotification();
                    }
 */