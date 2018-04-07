package com.news.dhruvkalia.news_on_the_go.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.news.dhruvkalia.news_on_the_go.Adapters.StoryAdapter;
import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.news.dhruvkalia.news_on_the_go.Model.Story;
import com.news.dhruvkalia.news_on_the_go.Utils.NewsDataShare;
import com.news.dhruvkalia.news_on_the_go.R;

import android.speech.tts.TextToSpeech;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SavedStoryActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    RecyclerView parentRecyclerView;
    List<Article> masterArticleList = new ArrayList<Article>();
    private String TAG = "SavedArticleActivity.java";
    private boolean isLoading = false;
    private int pageNumber = 1;
    private LinearLayoutManager layoutManager;
    private Context globalContext = this;
    private StoryAdapter storyAdapter;
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

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();

        Realm realm;
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();


        RealmResults<Story> results = realm.where(Story.class).findAll();

        storyAdapter = new StoryAdapter(results, globalContext);
        parentRecyclerView.setAdapter(storyAdapter);
    }

}
