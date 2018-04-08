package com.news.dhruvkalia.news_on_the_go.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;


import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.news.dhruvkalia.news_on_the_go.Model.Story;
import com.news.dhruvkalia.news_on_the_go.Utils.NewsDataShare;
import com.news.dhruvkalia.news_on_the_go.R;
import com.squareup.picasso.Picasso;


import java.text.DateFormatSymbols;
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

    public class StoryDetailActivity extends AppCompatActivity implements android.speech.tts.TextToSpeech.OnInitListener {

    StreamPlayer streamPlayer;
    String username = "90dbd655-6b09-4b0a-909a-5292abeeb0eb";
    String password = "pnl3xHEZT8gl";

    private android.speech.tts.TextToSpeech textToSpeech;
    private Story story = null;
    private FloatingActionButton headsetButton, saveButton;

    private Realm realm;

    private String TAG = "StoryDetail.java";
    private NewsDataShare newsDataShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }catch (NullPointerException ex){
            Log.d(TAG,"Exception is " + ex);
        }

        headsetButton = findViewById(R.id.fab);
        saveButton = findViewById(R.id.article_detail_save_article);

        textToSpeech = new android.speech.tts.TextToSpeech(this,this);

        int idOfStoryRecieved = getIntent().getIntExtra("id",0);

        newsDataShare = NewsDataShare.getSharedInstance();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();

        Log.d(TAG,"id of story in detail is " + idOfStoryRecieved);
        Log.d(TAG,"Looking in singleton now... size of singeleton stories is " + newsDataShare.mainPojoResponse.getStories().size());

        if(newsDataShare.mainPojoResponse != null){
            for(int i = 0; i< newsDataShare.mainPojoResponse.getStories().size(); i++){
                Story storyFound = newsDataShare.mainPojoResponse.getStories().get(i);
                if((storyFound.getId()) == (idOfStoryRecieved)){
                    story = storyFound;
                    Log.d(TAG,"Story found in singeleton object only: title is" + storyFound.getTitle());
                    break;
                }
            }
        }

        if(story == null && idOfStoryRecieved != 0){
            //if no internet and article is being viewed from SavedArticles
            Log.d(TAG,"Story was NOT found in singeleton");
            Log.d(TAG,"Looking in realm now...");

            Story storyFound = realm.where(Story.class).equalTo("id",idOfStoryRecieved).findFirst();
            try{
                story = storyFound;
                Log.d(TAG,"Found in realm...");

            }catch (ArrayIndexOutOfBoundsException ex){
                Log.e(TAG,"Exception is " + ex);
            }

        }


        Story storyFound = realm.where(Story.class).equalTo("id",idOfStoryRecieved).findFirst();
            try{
                if(storyFound == null){
                    Log.d(TAG, "save button VISIBLE");
                    saveButton.setVisibility(View.VISIBLE);
                } else{
                    Log.d(TAG, "save button GONE");
                    saveButton.setVisibility(View.GONE);
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                Log.e("ArticleDetail","Exception is " + ex);
                Log.d(TAG, "save button VISIBLE");
                saveButton.setVisibility(View.VISIBLE);
            }


        TextView articleDetailTitle = findViewById(R.id.article_detail_title);
        TextView articleDetailAuthor = findViewById(R.id.article_detail_author);
        TextView articleDetailTime = findViewById(R.id.article_detail_time);
        TextView articleDetailDescription = findViewById(R.id.article_detail_description);
        ImageView articleDetailImage = findViewById(R.id.article_detail_image);

        try{
            articleDetailTitle.setText(story.getTitle());
            articleDetailAuthor.setText(story.getAuthor().getName());
            articleDetailDescription.setText(story.getBody());

            //2016-08-22T16:46:48Z
            String year = story.getPublishedAt().substring(0,4);
            String monthName = (new DateFormatSymbols()).getMonths()[Integer.parseInt(story.getPublishedAt().substring(5,7)) -1];
            String date = story.getPublishedAt().substring(8,10);

            articleDetailTime.setText(monthName + " " + date + ", " + year);
        } catch (NullPointerException ex){
            Log.e("StoryDetailActivity", "NullPointerException is " + ex);
        }catch (IllegalArgumentException ex){
            Log.e("StoryDetailActivity", "IllegalArgumentException is " + ex);
        }catch (Exception ex){
            Log.e("StoryDetailActivity", "Exception is " + ex);
        }


        try{
            Picasso.get()
                    .load(story.getMedia().get(0).getUrl())
                    .into(articleDetailImage);

        }catch (NullPointerException ex){
            Log.e("StoryAdapter", "NullPointerException is " + ex);
        }catch (IllegalArgumentException ex){
            Log.e("StoryAdapter", "IllegalArgumentException is " + ex);
        }catch (Exception ex){
            Log.e("StoryAdapter", "Exception is " + ex);
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

                Log.d(TAG,"Current story's title is " + story.getTitle());

                try{
                    Story storyFound = realm.where(Story.class).equalTo("id",story.getId()).findFirst();
                    if(storyFound != null){
                        //Story already exists
                        saveButton.setVisibility(View.GONE);
                        Snackbar.make(v, "Story is already saved. You can view it in offline mode.", Snackbar.LENGTH_LONG)
                                .setAction("View Offline", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(StoryDetailActivity.this, SavedStoryActivity.class));
                                    }
                                }).show();
                    } else{
                        RealmResults<Story> results = realm.where(Story.class).findAll();
                        for(int i = 0; i < results.size(); i++){
                            Log.d(TAG,"Saved story id of #" + i + " is " + results.get(i).getId());
                        }
                        Log.d(TAG,"Saving story's id is " + story.getId());
                        realm.beginTransaction();
                        realm.copyToRealm(story);
                        realm.commitTransaction();
                        Snackbar.make(v, "Story is saved. You can view it in offline mode.", Snackbar.LENGTH_LONG)
                                .setAction("View Offline", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(StoryDetailActivity.this, SavedStoryActivity.class));
                                    }
                                }).show();

                        saveButton.setVisibility(View.GONE);

                    }
                } catch (NullPointerException ex){
                    Log.d(TAG,"Exception is " + ex);
                }

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
                Intent intent = new Intent(StoryDetailActivity.this,WebViewActivity.class);
                intent.putExtra("url",story.getLinks().getPermalink());
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

            streamPlayer.playStream(textToSpeech.synthesize(story.getTitle(), Voice.EN_LISA).execute());
            streamPlayer.playStream(textToSpeech.synthesize(story.getBody(), Voice.EN_LISA).execute());

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

        String text = story.getBody();

        textToSpeech.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
    }
}
