package com.example.dburtnja.androidticketfinder10;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;
import com.google.gson.Gson;

public class Main2Activity extends AppCompatActivity {
    private WebView         webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Ticket              ticket;
        Gson                gson;

        gson = new Gson();
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        ticket = gson.fromJson(savedInstanceState.getString("ticket"), Ticket.class);
        if (ticket.isHaveTicket())
            webView.loadUrl(showCart(ticket));
        else
            webView.loadUrl(showSearchDay(ticket));
    }

    private String showSearchDay(Ticket ticket){
        String  url;

        url = "http://booking.uz.gov.ua/?" +
                "date=" + ticket.dateFromStart.getStrDate() +
                "&from=" + ticket.getStationFrom().getValue() +
                "&time=" + ticket.dateFromStart + //might need : parse
                "&to=" + ticket.getStationTill().getValue() +
                "&url=train-list";
        return (url);
    }

    private String showCart(Ticket ticket){
        ClipboardManager    clipboardManager;
        ClipData            clipData;
        CookieManager       cookieManager;
        String              url;

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("cookie", ticket.cookie);
        clipboardManager.setPrimaryClip(clipData);
        url = "https://booking.uz.gov.ua/mobile/cart/";
        cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("https://booking.uz.gov.ua/", ticket.cookie);
        return (url);
    }
}
