package com.news.dhruvkalia.news_on_the_go.Utils;

import com.news.dhruvkalia.news_on_the_go.Model.MainPojoResponse;

/**
 * Created by Dhruv Kalia on 4/4/2018.
 */

public class NewsDataShare {

    private static NewsDataShare sharedInstance ;

    public MainPojoResponse mainPojoResponse = null;
    private NewsDataShare(){}

    public static NewsDataShare getSharedInstance(){
        if(sharedInstance == null){
            synchronized (NewsDataShare.class) {
                if(sharedInstance == null){
                    sharedInstance = new NewsDataShare();
                }
            }
        }
        return sharedInstance;
    }
}
