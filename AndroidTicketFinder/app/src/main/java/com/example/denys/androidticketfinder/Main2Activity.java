package com.example.denys.androidticketfinder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.denys.androidticketfinder.Search.Search;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private WebView         webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ClipboardManager clipboardManager;
        ClipData clipData;
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("https://booking.uz.gov.ua/", cookie);
        webView.loadUrl(url);
    }
}
