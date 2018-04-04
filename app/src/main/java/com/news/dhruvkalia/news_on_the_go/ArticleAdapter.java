package com.news.dhruvkalia.news_on_the_go;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dhruv Kalia on 3/29/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> articleList;
    private Context context;


    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_card, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.MyViewHolder myViewHolder, int i) {

        /*// if last element is going to be binded
        if(i == (numberOfPages*20) - 1 ){
           // MainActivity mainActivity = (MainActivity) context;
            if(listener != null)
                listener.getResponsesFromNewsApi(numberOfPages+1, context);
        } else */
        {
            final Article article = articleList.get(i);

            try {
                myViewHolder.cardArticleTitle.setText(article.getTitle());
                myViewHolder.cardArticleDescription.setText(article.getDescription());
                myViewHolder.cardArticleAuthor.setText(article.getAuthor());
                myViewHolder.cardArticleTime.setText(article.getPublishedAt());
            }catch (NullPointerException ex){
                Log.e("ArticleAdapter", "NullPointerException is " + ex);
            }catch (IllegalArgumentException ex){
                Log.e("ArticleAdapter", "IllegalArgumentException is " + ex);
            }catch (Exception ex){
                Log.e("ArticleAdapter", "Exception is " + ex);
            }

            try{
                Picasso.get()
                        .load(article.getUrlToImage())
                        .resize(100, 100)
                        .centerCrop()
                        .into(myViewHolder.cardArticleImage);


            }catch (NullPointerException ex){
                Log.e("ArticleAdapter", "NullPointerException is " + ex);
            }catch (IllegalArgumentException ex){
                Log.e("ArticleAdapter", "IllegalArgumentException is " + ex);
            }

            myViewHolder.cardArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ArticleDetailActivity.class );
                    intent.putExtra("article", article);
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardArticleTitle, cardArticleDescription, cardArticleAuthor, cardArticleTime;
        ImageView cardArticleImage;
        CardView cardArticle;


        public MyViewHolder(View itemView) {
            super(itemView);

            cardArticleTitle = (TextView) itemView.findViewById(R.id.card_article_title);
            cardArticleDescription = (TextView) itemView.findViewById(R.id.card_article_description);
            cardArticleAuthor = (TextView) itemView.findViewById(R.id.card_article_author);
            cardArticleTime = (TextView) itemView.findViewById(R.id.card_article_time);
            cardArticleImage = (ImageView) itemView.findViewById(R.id.card_article_image);
            cardArticle = (CardView) itemView.findViewById(R.id.card_article);

        }


    }
}
