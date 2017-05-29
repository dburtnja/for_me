package com.example.denys.androidticketfinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

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
        Gson gson = new Gson();
        Ticket ticket = gson.fromJson(intent.getStringExtra("ticket"), Ticket.class);
        Search search = new Search(MyService.this, ticket);
        search.send();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
