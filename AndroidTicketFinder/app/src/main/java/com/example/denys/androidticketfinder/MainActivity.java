package com.example.denys.androidticketfinder;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.denys.androidticketfinder.Search.Search;
import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.example.denys.androidticketfinder.getStation.ReceiveStation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    public EditText fromStation;
    public EditText tillStation;
    public Button button;
    public Ticket ticket = new Ticket();
    public TextView statusView;
    public TextView fromData;
    public TextView fromTime;
    public TextView tillData;
    public TextView tillTime;
    public Button stop;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        fromStation = (EditText) findViewById(R.id.fromStation);
        tillStation = (EditText) findViewById(R.id.tillStation);
        statusView = (TextView) findViewById(R.id.statusView);
        fromData = (TextView) findViewById(R.id.fromData);
        tillData = (TextView) findViewById(R.id.tillData);
        fromTime = (TextView) findViewById(R.id.fromTime);
        tillTime = (TextView) findViewById(R.id.tillTime);
        stop = (Button) findViewById(R.id.stop);
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Дата: dd.MM.yyyy-Час: HH:mm");

        final int monthP;

        monthP = month + 1;
        fromData.setText("Дата: " + (day < 10 ? "0" + day : day) + "." + (monthP < 10 ? "0" + monthP : monthP) + "." + year);
        tillData.setText("Дата: " + (day < 10 ? "0" + day : day) + "." + (monthP < 10 ? "0" + monthP : monthP) + "." + year);
        try {
            ticket.fromDate = simpleDateFormat.parse(fromData.getText() + "-" + fromTime.getText()).getTime();
            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("ticket time", fromData.getText() + "-" + fromTime.getText());
        Log.d("ticket time", ticket.fromDate + "");
        Log.d("timezone", calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE));


        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fromTime.setText("Час: " + (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) +
                                ":" + (minute < 10 ? "0" + minute : minute));
                        try {
                            ticket.fromDate = simpleDateFormat.parse(fromData.getText() + "-" + fromTime.getText()).getTime();
                            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        tillTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tillTime.setText("Час: " + (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) +
                                ":" + (minute < 10 ? "0" + minute : minute));
                        try {
                            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (ticket.tillDate < ticket.fromDate) {
                            ticket.tillDate = ticket.fromDate;
                            tillData.setText(new SimpleDateFormat("Дата: dd.MM.yyyy").format(new Date(ticket.tillDate)));
                            tillTime.setText(new SimpleDateFormat("Час: HH:mm").format(new Date(ticket.tillDate)));
                        }
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        fromData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthP = month + 1;
                        fromData.setText("Дата: " +
                                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." +
                                (monthP < 10 ? "0" + monthP : monthP) + "." + year);
                        tillData.setText("Дата: " +
                                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." +
                                (monthP < 10 ? "0" + monthP : monthP) + "." + year);
                        try {
                            ticket.fromDate = simpleDateFormat.parse(fromData.getText() + "-" + fromTime.getText()).getTime();
                            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        tillData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthP = month + 1;
                        tillData.setText("Дата: " +
                                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." +
                                (monthP < 10 ? "0" + monthP : monthP) + "." + year);
                        try {
                            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(ticket.fromDate);
                datePickerDialog.show();
            }
        });

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
                if (ticket.fromStation.value != 0 || ticket.tillStation.value != 0) {
                    Log.d("sdas","a");
                    startService(new Intent(MainActivity.this, Search.class));
                } else {
                    statusView.setText("Помилка пошуку! Пошук не розпочато!!!");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, Search.class));
            }
        });
    }
}