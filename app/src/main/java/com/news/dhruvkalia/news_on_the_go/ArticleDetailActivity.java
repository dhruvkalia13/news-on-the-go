package com.news.dhruvkalia.news_on_the_go;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.news.dhruvkalia.news_on_the_go.Model.Article;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();
        Article article = intent.getParcelableExtra("article");

        ((TextView) findViewById(R.id.text_view)).setText(article.getTitle());


    }
}
