package com.example.denys.androidticketfinder;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.example.denys.androidticketfinder.getStation.ReceiveStation;

import org.json.JSONException;

import java.net.URI;


public class MainActivity extends AppCompatActivity {
    public EditText fromStation;
    public EditText tillStation;
    public Button button;
    public Ticket ticket = new Ticket();
    public TextView statusView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        fromStation = (EditText) findViewById(R.id.fromStation);
        tillStation = (EditText) findViewById(R.id.tillStation);
        statusView = (TextView) findViewById(R.id.statusView);

        fromStation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    statusView.setText("");
                    new ReceiveStation(fromStation, ticket.fromStation, statusView);
                }
            }
        });

        tillStation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    statusView.setText("");
                    new ReceiveStation(tillStation, ticket.tillStation, statusView);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusView.setText("");
                statusView.setText(ticket.fromStation.label + " " + ticket.fromStation.value);
                Log.d("from", ticket.fromStation.label + ticket.fromStation.value);
            }
        });

    }


}


/*

try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
 */