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

import com.example.denys.androidticketfinder.Main2Activity;
import com.example.denys.androidticketfinder.MainActivity;
import com.example.denys.androidticketfinder.Search.train_search.Post;
import com.example.denys.androidticketfinder.Search.train_search.search.SelectTrain;
import com.example.denys.androidticketfinder.Search.train_search.search.TrainSearch;
import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Denys on 08.05.2017.
 */

public class Search{
    private Context context;
    private Ticket ticket;

    public void send() {
        this.thread.start();
    }

    public void onCheckNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentTitle("Перевірив" + ticket.fromStation.title + "=>" + ticket.tillStation.title)
                .setContentText(ticket.status)
                .setContentInfo("INfo");

        NotificationManager nM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nM.notify(1, builder.build());
    }

    public Search(Context context, Ticket ticket) {
        this.context = context;
        this.ticket = ticket;
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            findTicket();
        }
    });

    protected void findTicket() {
        Ticket ticket;
        final Post post = new Post();
        final SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy");
        final SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        ticket = this.ticket;
        Date fromData = new Date(ticket.fromDate);
        Date tillData = new Date(ticket.tillDate);
        String train_searchParam =
                "station_id_from=" + ticket.fromStation.value +
                        "&station_id_till=" + ticket.tillStation.value +
                        "&station_from=" +
                        "&station_till=" +
                        "&date_dep=" + simpleDate.format(fromData) +
                        "&time_dep=" + simpleTime.format(fromData) +
                        "&time_dep_till=" + simpleTime.format(tillData) +
                        "&another_ec=0" +
                        "&search=";
        Object obj = post.sendPost("http://booking.uz.gov.ua/purchase/search/", train_searchParam, TrainSearch.class, ticket);
        if (obj != null) {
            SelectTrain selectTrain = new SelectTrain();
            if (selectTrain.findPlace((TrainSearch)obj, ticket, post)) {

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                MediaPlayer player = MediaPlayer.create(context, notification);
                player.setLooping(true);
                player.start();
               /* Search.this.timer.cancel();
                Intent intent1 = new Intent(Search.this, Main2Activity.class);
                intent1.putExtra("_gv_sessid", ticket.cookie);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);*/
            }
            else
                onCheckNotification();
        } else
            onCheckNotification();
    }
}