package com.example.dburtnja.androidticketfinder10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;
import com.example.dburtnja.androidticketfinder10.TicketInfo.TicketDate;

public class MainActivity extends AppCompatActivity {
    private Ticket          ticket;
    private EditText        getStationFrom;
    private EditText        getStationTill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton     getReplaceStations;

        getStationFrom = (EditText) findViewById(R.id.stationFrom);
        getStationTill = (EditText) findViewById(R.id.stationTill);
        getReplaceStations = (ImageButton) findViewById(R.id.replaceStations);

        ticket = new Ticket(this);
        ticket.dateFromStart = new TicketDate(this, R.id.dateFromStart, R.id.timeFromStart);
        ticket.dateFromEnd = new TicketDate(this, R.id.dateFromEnd, R.id.timeFromEnd);

        getStationFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationFrom.getText().toString().equals("")) {
                    ticket.setStationFrom(getStationFrom);
                }
            }
        });

        getStationTill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationTill.getText().toString().equals("")) {
                    ticket.setStationTill(getStationTill);
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
                    ticket.dateFromStart.changeDate(ticket);
                else if (view.getId() == R.id.dateFromEnd)
                    ticket.dateFromEnd.changeDate(ticket);
            }
        };

        View.OnClickListener clickOnTime = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.timeFromStart)
                    ticket.dateFromStart.changeTime(ticket);
                else if (view.getId() == R.id.timeFromEnd)
                    ticket.dateFromEnd.changeTime(ticket);
            }
        };

        findViewById(R.id.dateFromStart).setOnClickListener(clickOnDate);
        findViewById(R.id.dateFromEnd).setOnClickListener(clickOnDate);
        findViewById(R.id.timeFromStart).setOnClickListener(clickOnTime);
        findViewById(R.id.timeFromEnd).setOnClickListener(clickOnTime);
    }

}
