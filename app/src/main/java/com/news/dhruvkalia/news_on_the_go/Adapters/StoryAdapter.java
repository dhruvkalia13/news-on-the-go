package com.news.dhruvkalia.news_on_the_go.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.dhruvkalia.news_on_the_go.Activity.StoryDetailActivity;
import com.news.dhruvkalia.news_on_the_go.Model.Story;
import com.news.dhruvkalia.news_on_the_go.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by Dhruv Kalia on 3/29/2018.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private List<Story> storyList;
    private Context context;


    public StoryAdapter(List<Story> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.story_card, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.MyViewHolder myViewHolder, final int i) {

        /*// if last element is going to be binded
        if(i == (numberOfPages*20) - 1 ){
           // MainActivity mainActivity = (MainActivity) context;
            if(listener != null)
                listener.getResponsesFromNewsApi(numberOfPages+1, context);
        } else */
        {
            final Story story = storyList.get(i);

            try {
                myViewHolder.cardArticleTitle.setText(story.getTitle());
                myViewHolder.cardArticleDescription.setText(story.getBody());
                myViewHolder.cardArticleAuthor.setText(story.getAuthor().getName());

                //2016-08-22T16:46:48Z
                String year = story.getPublishedAt().substring(0,4);
                String monthName = (new DateFormatSymbols()).getMonths()[Integer.parseInt(story.getPublishedAt().substring(5,7)) -1];
                String date = story.getPublishedAt().substring(8,10);

                myViewHolder.cardArticleTime.setText(monthName + " " + date + ", " + year);
            }catch (NullPointerException ex){
                Log.e("StoryAdapter", "NullPointerException is " + ex);
            }catch (IllegalArgumentException ex){
                Log.e("StoryAdapter", "IllegalArgumentException is " + ex);
            }catch (Exception ex){
                Log.e("StoryAdapter", "Exception is " + ex);
            }

            try{
                Picasso.get()
                        .load(story.getMedia().get(0).getUrl())
                        .resize(100, 100)
                        .centerCrop()
                        .into(myViewHolder.cardArticleImage);


            }catch (NullPointerException ex){
                Log.e("StoryAdapter", "NullPointerException is " + ex);
            }catch (IllegalArgumentException ex){
                Log.e("StoryAdapter", "IllegalArgumentException is " + ex);
            }catch (Exception ex){
                Log.e("StoryAdapter", "Exception is " + ex);
            }

            myViewHolder.cardArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, StoryDetailActivity.class );
                    intent.putExtra("id",story.getId());
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
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
