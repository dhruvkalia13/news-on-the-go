package com.news.dhruvkalia.news_on_the_go;

import android.content.Intent;
import android.os.Bundle;
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

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent = getIntent();
        Article article = intent.getParcelableExtra("article");

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
