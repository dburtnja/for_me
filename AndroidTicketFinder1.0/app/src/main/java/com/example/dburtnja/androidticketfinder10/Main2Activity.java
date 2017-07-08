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

public class Main2Activity extends AppCompatActivity {
    private WebView         webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ClipboardManager    clipboardManager;
        ClipData            clipData;
        CookieManager       cookieManager;
        String              url;
        String              cookie;

        cookie = savedInstanceState.getString("cookie");
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("cookie", cookie);
        clipboardManager.setPrimaryClip(clipData);
        url = "https://booking.uz.gov.ua/mobile/cart/";
        cookieManager = CookieManager.getInstance();
        webView = (WebView) findViewById(R.id.webView);
        queue = Volley.newRequestQueue(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("https://booking.uz.gov.ua/", cookie);
        webView.loadUrl(url);
    }
}
