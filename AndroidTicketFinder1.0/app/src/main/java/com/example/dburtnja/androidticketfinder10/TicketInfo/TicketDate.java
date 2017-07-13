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
    private transient SimpleDateFormat      sDateFormat;
    private transient SimpleDateFormat      sTimeFromat;
    private transient SimpleDateFormat      sFormat;
    private transient SimpleDateFormat      sDateTicketFormat;
    private transient SimpleDateFormat      sTimeTicketFormat;
    private int                             dateId;
    private int                             timeId;
    private long                            date;

    public long getDate() {
        return date;
    }

    public String getStrDate() {
        return sDateTicketFormat.format(date);
    }

    public String getStrTime(){
        return sTimeTicketFormat.format(date);
    }

    public TicketDate(MainActivity activity, int dateId, int timeId) {
        Calendar    c;
        TextView    dateView;
        TextView    timeView;

        setSimpleFormat();
        this.dateId = dateId;
        this.timeId = timeId;
        dateView = (TextView) activity.findViewById(dateId);
        timeView = (TextView) activity.findViewById(timeId);
        c = Calendar.getInstance();
        dateView.setText(sDateFormat.format(c.getTime().getTime()));
        try {
            date = sFormat.parse(dateView.getText() + "-" + timeView.getText()).getTime();
        } catch (ParseException e) {
            date = -1;
            dateView.setText("Помилка");
            timeView.setText("Помилка");
            e.printStackTrace();
        }
    }

    public void setSimpleFormat(){
        sDateFormat = new SimpleDateFormat("Дата: dd.MM.yyyy", Locale.getDefault());
        sTimeFromat = new SimpleDateFormat("Час: HH:mm", Locale.getDefault());
        sFormat = new SimpleDateFormat("Дата: dd.MM.yyyy-Час: HH:mm", Locale.getDefault());
        sDateTicketFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sTimeTicketFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    private void writeDate(int h, int m, MainActivity activity){
        TextView    dateView;
        TextView    timeView;

        dateView = (TextView) activity.findViewById(dateId);
        timeView = (TextView) activity.findViewById(timeId);
        timeView.setText("Час: " + (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m));
        try {
            date = sFormat.parse(dateView.getText() + "-" + timeView.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void writeDate(int y, int m, int d, MainActivity activity){
        TextView    dateView;
        TextView    timeView;

        dateView = (TextView) activity.findViewById(dateId);
        timeView = (TextView) activity.findViewById(timeId);
        dateView.setText("Дата: " + (d < 10 ? "0" + d : d) + "." + (m < 10 ? "0" + m : m) + "." + y);
        try {
            date = sFormat.parse(dateView.getText() + "-" + timeView.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void changeTime(final Ticket ticket, final MainActivity activity){
        TimePickerDialog    timePickerDialog;

        timePickerDialog = new TimePickerDialog(activity, 0, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                writeDate(h, m, activity);
                ticket.dateFromEnd.setEndDayIfNeeded(ticket.dateFromStart, activity);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }

    public void changeDate(final Ticket ticket, final MainActivity activity){
        DatePickerDialog    datePickerDialog;
        Calendar            cal;

        cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        datePickerDialog = new DatePickerDialog(activity, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                writeDate(y, m, d, activity);
                ticket.dateFromEnd.setEndDayIfNeeded(ticket.dateFromStart, activity);
            }
        },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void setEndDayIfNeeded(TicketDate start, MainActivity activity){
        TextView    dateView;
        TextView    timeView;

        dateView = (TextView) activity.findViewById(dateId);
        timeView = (TextView) activity.findViewById(timeId);
        if (this.date <= start.date) {
            this.date = start.getNextDayTime() - 60000; //next day minus 1 sec
            dateView.setText(sDateFormat.format(date));
            timeView.setText(sTimeFromat.format(date));
        }
    }

    public long getNextDayTime() {
        SimpleDateFormat    simpleDate;
        Date                nextDayDate;
        Date                plusDay;

        simpleDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        nextDayDate = null;
        plusDay = new Date(date + 86400000); // add one day
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
