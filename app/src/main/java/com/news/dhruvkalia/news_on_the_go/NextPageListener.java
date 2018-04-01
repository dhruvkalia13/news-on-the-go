package com.news.dhruvkalia.news_on_the_go;

import android.content.Context;

/**
 * Created by Dhruv Kalia on 4/2/2018.
 */

public interface NextPageListener {

    public void getResponsesFromNewsApi(int pageNumber, Context context);
}
