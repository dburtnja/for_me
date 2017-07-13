package com.example.dburtnja.androidticketfinder10;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;
import com.example.dburtnja.androidticketfinder10.TicketInfo.TicketDate;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Train;
import com.google.gson.Gson;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Ticket          ticket;
    private EditText        getStationFrom;
    private EditText        getStationTill;
    private CheckBox        checkBoxArray[];
    private PendingIntent   pendingIntent;
    private AlarmManager    alarmManager;
    private Gson            gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton     getReplaceStations;
        final Button    startButton = (Button) findViewById(R.id.start);
        Button          stopButton;
        Button          lookUp;
        final RequestQueue    queue;

        queue = Volley.newRequestQueue(this);
        gson = new Gson();
        lookUp = (Button) findViewById(R.id.lookUp);
        stopButton = (Button) findViewById(R.id.stop);
        getStationFrom = (EditText) findViewById(R.id.stationFrom);
        getStationTill = (EditText) findViewById(R.id.stationTill);
        getReplaceStations = (ImageButton) findViewById(R.id.replaceStations);

        ticket = new Ticket(new Train());
        ticket.dateFromStart = new TicketDate(this, R.id.dateFromStart, R.id.timeFromStart);
        ticket.dateFromEnd = new TicketDate(this, R.id.dateFromEnd, R.id.timeFromEnd);

        getStationFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationFrom.getText().toString().equals("")) {
                    ticket.setStationFrom(getStationFrom, queue, MainActivity.this);
                }
            }
        });

        getStationTill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationTill.getText().toString().equals("")) {
                    ticket.setStationTill(getStationTill, queue, MainActivity.this);
                }
            }
        });

        getReplaceStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticket.replaceStations(getStationFrom, getStationTill);
            }
        });

        View.OnClickListener clickOnDate = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.dateFromStart)
                    ticket.dateFromStart.changeDate(ticket, MainActivity.this);
                else if (view.getId() == R.id.dateFromEnd)
                    ticket.dateFromEnd.changeDate(ticket, MainActivity.this);
            }
        };

        View.OnClickListener clickOnTime = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.timeFromStart)
                    ticket.dateFromStart.changeTime(ticket, MainActivity.this);
                else if (view.getId() == R.id.timeFromEnd)
                    ticket.dateFromEnd.changeTime(ticket, MainActivity.this);
            }
        };

        View.OnClickListener clickOnPlace = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticket.getTrain().changeCoach(checkBoxArray);
            }
        };

        findViewById(R.id.dateFromStart).setOnClickListener(clickOnDate);
        findViewById(R.id.dateFromEnd).setOnClickListener(clickOnDate);
        findViewById(R.id.timeFromStart).setOnClickListener(clickOnTime);
        findViewById(R.id.timeFromEnd).setOnClickListener(clickOnTime);

        checkBoxArray = new CheckBox[]{
                (CheckBox) findViewById(R.id.checkP),
                (CheckBox) findViewById(R.id.checkK),
                (CheckBox) findViewById(R.id.checkC1),
                (CheckBox) findViewById(R.id.checkC2)
        };

        for (CheckBox checkBox : checkBoxArray){
            checkBox.setOnClickListener(clickOnPlace);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent      intent;

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                ticket.setName(R.id.firstName, R.id.lastName, MainActivity.this);
                if (ticket.checkIfAllSet(MainActivity.this)){
                    startButton.setEnabled(false);
                    intent = new Intent(MainActivity.this, MyService.class);
                    intent.putExtra("ticket", gson.toJson(ticket));
                    pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
                   // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
                }
            }
        });

        lookUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent;

                intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("ticket", gson.toJson(ticket));
                startActivity(intent);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setEnabled(true);
            }
        });
    }

    public boolean toast(String msg, Boolean vibrate) {
        if (vibrate) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            Vibrator vibrator;
            vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            return false;
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
    }
}
