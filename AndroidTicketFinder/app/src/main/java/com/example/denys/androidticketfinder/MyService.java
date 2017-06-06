package com.example.denys.androidticketfinder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.example.denys.androidticketfinder.Search.Search;
import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.google.gson.Gson;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Gson            gson;
        Ticket          ticket;
        Search          search;
        PendingIntent   pendingIntent;
        AlarmManager    alarmManager;

        gson = new Gson();
        ticket = gson.fromJson(intent.getStringExtra("ticket"), Ticket.class);
        search = new Search(MyService.this, ticket);
        search.findTicket();
        pendingIntent = PendingIntent.getService(this, 10, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentTitle("Пошук зупинений")
                .setContentInfo("stop");

        NotificationManager nM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        nM.notify(1, builder.build());
    }
}
