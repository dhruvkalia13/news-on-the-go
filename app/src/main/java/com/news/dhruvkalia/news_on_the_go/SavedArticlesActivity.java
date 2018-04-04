package com.news.dhruvkalia.news_on_the_go;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.news.dhruvkalia.news_on_the_go.Model.Article;
import android.speech.tts.TextToSpeech;
import java.util.Locale;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SavedArticlesActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    RecyclerView parentRecyclerView;
    List<Article> masterArticleList = new ArrayList<Article>();
    private String TAG = "SavedArticleActivity.java";
    private boolean isLoading = false;
    private int pageNumber = 1;
    private LinearLayoutManager layoutManager;
    private Context globalContext = this;
    private ArticleAdapter articleAdapter;
    private NewsDataShare newsDataShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        textToSpeech = new TextToSpeech(this, this);

        parentRecyclerView = findViewById(R.id.parent_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        parentRecyclerView.setLayoutManager(layoutManager);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        Realm realm;
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();


        RealmResults<Article> results = realm.where(Article.class).findAll();

        articleAdapter = new ArticleAdapter(results, globalContext);
        parentRecyclerView.setAdapter(articleAdapter);
    }

}
