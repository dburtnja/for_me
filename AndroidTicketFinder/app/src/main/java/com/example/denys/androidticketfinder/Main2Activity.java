package com.example.denys.androidticketfinder;

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
import android.widget.TextView;

import com.example.denys.androidticketfinder.Search.Search;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Bundle extras = getIntent().getExtras();
        String str = extras.getString("_gv_sessid");
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer player = MediaPlayer.create(Main2Activity.this, notification);
        player.setLooping(true);
        player.start();
        if (str != null) {
            Log.d("cookie", str);
            WebView webview = (WebView) this.findViewById(R.id.webView);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
            CookieManager.getInstance().setCookie("http://booking.uz.gov.ua/", "_gv_sessid=" + str);
            webview.loadUrl("http://booking.uz.gov.ua/mobile/cart/");
        }
    }
}
