package com.example.denys.androidticketfinder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.denys.androidticketfinder.Search.Search;
import com.example.denys.androidticketfinder.Ticket.Place;
import com.example.denys.androidticketfinder.Ticket.Ticket;
import com.example.denys.androidticketfinder.getStation.ReceiveStation;
import com.google.gson.Gson;

import java.net.CookieManager;
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
    public Intent intent;
    private TextView checkTime;
    private CheckBox cbAny;
    private CheckBox cbP;
    private CheckBox cbK;
    private CheckBox cbC1;
    private CheckBox cbC2;
    private TextView reservation;
    public boolean resFlag = false;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private void playMusic(int type){
        Uri notification = RingtoneManager.getDefaultUri(type);
        Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playMusic(RingtoneManager.TYPE_RINGTONE);
    }

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
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        checkTime = (TextView) findViewById(R.id.cheackTime);
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Дата: dd.MM.yyyy-Час: HH:mm");
        final int monthP;

        seekBar.setEnabled(true); //free
        tillData.setEnabled(false);
        ticket.seekBarVal = 20;
        cbAny = (CheckBox) findViewById(R.id.cbAny);
        cbP = (CheckBox) findViewById(R.id.cbP);
        cbK = (CheckBox) findViewById(R.id.cbK);
        cbC1 = (CheckBox) findViewById(R.id.cbC1);
        cbC2 = (CheckBox) findViewById(R.id.cbC2);


        cbAny.setChecked(true);
        monthP = month + 1;
        fromData.setText("Дата: " + (day < 10 ? "0" + day : day) + "." + (monthP < 10 ? "0" + monthP : monthP) + "." + year);
        tillData.setText("Дата: " + (day < 10 ? "0" + day : day) + "." + (monthP < 10 ? "0" + monthP : monthP) + "." + year);
        try {
            ticket.fromDate = simpleDateFormat.parse(fromData.getText() + "-" + fromTime.getText()).getTime();
            ticket.tillDate = simpleDateFormat.parse(tillData.getText() + "-" + tillTime.getText()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                checkTime.setText((progress + 1) + " х.");
                ticket.seekBarVal = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //first and last name appears
        findViewById(R.id.reservation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resFlag) {
                    findViewById(R.id.firstName).setVisibility(View.VISIBLE);
                    findViewById(R.id.lastName).setVisibility(View.VISIBLE);
                    resFlag = true;
                } else {
                    findViewById(R.id.firstName).setVisibility(View.INVISIBLE);
                    findViewById(R.id.lastName).setVisibility(View.GONE);
                    resFlag = false;
                }
            }
        });


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
                Gson gson = new Gson();
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (ticket.fromStation.value != 0 && ticket.tillStation.value != 0) {
                    ticket.place = new Place(cbAny, cbK, cbP, cbC1, cbC2);
                    statusView.setText("Веду пошук: з станції " + ticket.fromStation.title + " до станції " +
                            ticket.tillStation.title);
                    button.setEnabled(false);

                    intent = new Intent(MainActivity.this, MyService.class);
                    intent.putExtra("ticket", gson.toJson(ticket));
                    pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
                    Log.d("time", SystemClock.elapsedRealtime() + "");
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 10000, 60000 * ticket.seekBarVal, pendingIntent);

                } else {
                    statusView.setText("Помилка пошуку! Пошук не розпочато!!!");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pendingIntent != null)
                   alarmManager.cancel(pendingIntent);
                button.setEnabled(true);
                statusView.setText("Пошук зупинено!");
            }
        });
    }

    public void checkBoxListener(View view) {
        if (cbAny == view && cbAny.isChecked()) {
            cbP.setChecked(false);
            cbK.setChecked(false);
            cbC1.setChecked(false);
            cbC2.setChecked(false);
        }
        else
            cbAny.setChecked(false);
    }

}