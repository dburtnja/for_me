package com.example.dburtnja.androidticketfinder10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;

public class MainActivity extends AppCompatActivity {
    private Ticket          ticket;
    private EditText        getStationFrom;
    private EditText        getStationTill;
    private ImageButton     getReplaceStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticket = new Ticket(this);
        getStationFrom = (EditText) findViewById(R.id.stationFrom);
        getStationTill = (EditText) findViewById(R.id.stationTill);
        getReplaceStations = (ImageButton) findViewById(R.id.replaceStations);

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
                ticket.replaceStantions();
                getStationFrom.setText(ticket.getStationFrom().getTitle());
                getStationTill.setText(ticket.getStationTill().getTitle());
            }
        });
    }


}
