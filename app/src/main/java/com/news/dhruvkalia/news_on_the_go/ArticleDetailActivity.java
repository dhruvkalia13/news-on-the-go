package com.news.dhruvkalia.news_on_the_go;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.squareup.picasso.Picasso;


import java.util.Locale;

public class ArticleDetailActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private Article article;
    private FloatingActionButton headsetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        textToSpeech = new TextToSpeech(this, this);
        Intent intent = getIntent();
        article = intent.getParcelableExtra("article");

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

         headsetButton = (FloatingActionButton) findViewById(R.id.fab);
        headsetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                speakOut();
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
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

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
