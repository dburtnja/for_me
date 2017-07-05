package com.example.dburtnja.androidticketfinder10.TicketInfo;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dburtnja.androidticketfinder10.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dburtnja on 05.07.17.
 * object store ticket time
 */

public class TicketDate {
    private MainActivity        mainActivity;
    private SimpleDateFormat    sDateFormat;
    private SimpleDateFormat    sFormat;
    private TextView            dateView;
    private TextView            timeView;
    private long                date;

    public TicketDate(MainActivity activity, int dateId, int timeId) {
        Calendar    c;

        dateView = (TextView) activity.findViewById(dateId);
        timeView = (TextView) activity.findViewById(timeId);
        sDateFormat = new SimpleDateFormat("Дата: dd.MM.yyyy", Locale.getDefault());
        sFormat = new SimpleDateFormat("Дата: dd.MM.yyyy-Час: HH:mm", Locale.getDefault());
        c = Calendar.getInstance();
        date = c.getTime().getTime();
        dateView.setText(sDateFormat.format(c.getTime()));
        mainActivity = activity;
    }

    private void writeDate(int h, int m){
        timeView.setText("Час: " + (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m));
        try {
            date = sFormat.parse(dateView.getText() + "-" + timeView.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void writeDate(int y, int m, int d){
        dateView.setText("Дата: " + (d < 10 ? "0" + d : d) + "." + (m < 10 ? "0" + m : m) + "." + y);
        try {
            date = sFormat.parse(dateView.getText() + "-" + timeView.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void changeTime(){
        TimePickerDialog    timePickerDialog;

        timePickerDialog = new TimePickerDialog(mainActivity, 0, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                writeDate(h, m);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }

    public void changeDate(){
        DatePickerDialog    datePickerDialog;
        Calendar            cal;

        cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(mainActivity, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                writeDate(y, m, d);
            }
        },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void setEndDayIfNeeded(TicketDate start){
        if (this.date <= start.date) {
            this.date = start.getNextDayTime();
            writeDate(23, 59);
            dateView.setText(sDateFormat.format(new Date(date)));
        }
    }

    public long getNextDayTime() {
        SimpleDateFormat    simpleDate;
        Date                nextDayDate;
        Date                plusDay;

        simpleDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        nextDayDate = null;
        plusDay = new Date(date + 86400000);
        try {
            nextDayDate = simpleDate.parse(simpleDate.format(plusDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nextDayDate == null)
            return (date);
        else
            return (nextDayDate.getTime());
    }
}
