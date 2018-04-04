package com.news.dhruvkalia.news_on_the_go;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;



import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.squareup.picasso.Picasso;


import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/*
watson credentions - text to speech
    {
            "url": "https://stream.watsonplatform.net/text-to-speech/api",
            "username": "90dbd655-6b09-4b0a-909a-5292abeeb0eb",
            "password": "pnl3xHEZT8gl"
            }*/

    public class ArticleDetailActivity extends AppCompatActivity implements android.speech.tts.TextToSpeech.OnInitListener {

    StreamPlayer streamPlayer;
    String username = "90dbd655-6b09-4b0a-909a-5292abeeb0eb";
    String password = "pnl3xHEZT8gl";

    private android.speech.tts.TextToSpeech textToSpeech;
    private Article article = null;
    private FloatingActionButton headsetButton, saveButton;

    private Realm realm;

    private NewsDataShare newsDataShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        headsetButton = findViewById(R.id.fab);
        saveButton = findViewById(R.id.article_detail_save_article);

        textToSpeech = new android.speech.tts.TextToSpeech(this,this);

        String titleOfArticle = getIntent().getStringExtra("title");

        newsDataShare = NewsDataShare.getSharedInstance();


        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();

        if(newsDataShare.mainPojoResponse != null){
            for(int i = 0; i< newsDataShare.mainPojoResponse.getArticles().size(); i++){
                Article articleFound = newsDataShare.mainPojoResponse.getArticles().get(i);
                if((articleFound.getTitle()).equals(titleOfArticle)){
                    article = articleFound;
                    break;
                }
            }
        }

        if(article == null && titleOfArticle != null){
            //if no internet and article is being viewed from SavedArticles

            RealmResults<Article> results = realm.where(Article.class).contains("title",titleOfArticle).findAll();
            try{
                article = results.get(0);
            }catch (ArrayIndexOutOfBoundsException ex){
                Log.e("ArticleDetail","Exception is " + ex);
            }

        }

            RealmResults<Article> results = realm.where(Article.class).contains("title",titleOfArticle).findAll();
            try{
                if(results.get(0) == null){
                    Log.d("ArticleDetail.java", "save button VISIBLE");
                    saveButton.setVisibility(View.VISIBLE);
                } else{
                    Log.d("ArticleDetail.java", "save button GONE");
                    saveButton.setVisibility(View.GONE);
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                Log.e("ArticleDetail","Exception is " + ex);
                Log.d("ArticleDetail.java", "save button VISIBLE");
                saveButton.setVisibility(View.VISIBLE);
            }


        TextView articleDetailTitle = findViewById(R.id.article_detail_title);
        TextView articleDetailAuthor = findViewById(R.id.article_detail_author);
        TextView articleDetailTime = findViewById(R.id.article_detail_time);
        TextView articleDetailDescription = findViewById(R.id.article_detail_description);
        ImageView articleDetailImage = findViewById(R.id.article_detail_image);

        try{
            articleDetailTitle.setText(article.getTitle());
            articleDetailAuthor.setText(article.getAuthor());
            articleDetailTime.setText(article.getPublishedAt());
            articleDetailDescription.setText(article.getDescription());
        } catch (NullPointerException ex){
            Log.e("ArticleDetailActivity", "NullPointerException is " + ex);
        }catch (IllegalArgumentException ex){
            Log.e("ArticleDetailActivity", "IllegalArgumentException is " + ex);
        }catch (Exception ex){
            Log.e("ArticleDetailActivity", "Exception is " + ex);
        }


        try{
            Picasso.get()
                    .load(article.getUrlToImage())
                    .into(articleDetailImage);

        }catch (NullPointerException ex){
            Log.e("ArticleAdapter", "NullPointerException is " + ex);
        }catch (IllegalArgumentException ex){
            Log.e("ArticleAdapter", "IllegalArgumentException is " + ex);
        }

        headsetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if(MainActivity.internetAvailableFlag){
                    WatsonTask task = new WatsonTask();
                    task.execute(new String[]{});
                } else{
                speakOut();
                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                realm.beginTransaction();
                realm.copyToRealm(article);
                realm.commitTransaction();
                Snackbar.make(v, "Article is saved. You can view it in offline mode.", Snackbar.LENGTH_LONG)
                        .setAction("View Offline", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ArticleDetailActivity.this, SavedArticlesActivity.class));
                            }
                        }).show();

                saveButton.setVisibility(View.GONE);
                RealmResults<Article> results = realm.where(Article.class).findAll();
                Log.v("ArticleDetailActivity","Saved article's title is " + (results.get(0)).getTitle() + "..");

            }
        });

        Button readMoreButton = findViewById(R.id.article_detail_read_more);

        if(MainActivity.internetAvailableFlag){
            readMoreButton.setVisibility(View.VISIBLE);
        } else{
            readMoreButton.setVisibility(View.GONE);
        }
        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleDetailActivity.this,WebViewActivity.class);
                intent.putExtra("url",article.getUrl());
                startActivity(intent);
            }
        });
    }


    private com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech initTextToSpeechService(){
        com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech service = new com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech();
        service.setUsernameAndPassword(username, password);
        return service;
    }


    private class WatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    textView.setText("running the Watson thread");
                }
            });

            com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech textToSpeech = initTextToSpeechService();
            streamPlayer = new StreamPlayer();

            streamPlayer.playStream(textToSpeech.synthesize(article.getTitle(), Voice.EN_LISA).execute());
            streamPlayer.playStream(textToSpeech.synthesize(article.getDescription(), Voice.EN_LISA).execute());

            return "text to speech done";
        }

        @Override
        protected void onPostExecute(String result) {
//            textView.setText("TTS status: " + result);
        }

    }

    @Override
    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA
                    || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                headsetButton.setEnabled(true);
//                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void speakOut() {

        String text = article.getDescription();

        textToSpeech.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
    }
}
