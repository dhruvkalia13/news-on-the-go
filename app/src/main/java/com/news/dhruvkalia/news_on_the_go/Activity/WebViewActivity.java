package com.news.dhruvkalia.news_on_the_go.Activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.news.dhruvkalia.news_on_the_go.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = (findViewById(R.id.web_view));

        webView.getSettings().setJavaScriptEnabled(true);

        try{
            webView.loadUrl(getIntent().getStringExtra("url"));
        }catch (Exception ex){
            Log.d(getPackageName(),"Exceptin is " + ex);
            Snackbar.make(webView, "Sorry... Some error", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
