package com.news.dhruvkalia.news_on_the_go.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.news.dhruvkalia.news_on_the_go.Adapters.StoryAdapter;
import com.news.dhruvkalia.news_on_the_go.Model.MainPojoResponse;
import com.news.dhruvkalia.news_on_the_go.Model.Story;
import com.news.dhruvkalia.news_on_the_go.Utils.NextPageListener;
import com.news.dhruvkalia.news_on_the_go.R;
import com.news.dhruvkalia.news_on_the_go.Utils.NewsDataShare;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NextPageListener {

    RecyclerView parentRecyclerView;
    List<Story> masterStoryList = new ArrayList<>();
    private String TAG = "MainActivity.java";
    private boolean isLoading = false;
//    private int pageNumber = 1;
    private LinearLayoutManager layoutManager;
    private Context globalContext = this;
    private StoryAdapter storyAdapter;
    private NewsDataShare newsDataShare;

    private ImageView noInternet;
    private TextView seeOffline;
    public static boolean internetAvailableFlag = false;
    private static final String AYLIEN_APPLICATION_ID = "4c50fc3b";
    private static final String AYLIEN_APPLICATION_KEY = "c8ea3419e09f7bbc67e542dea1705a18";
    int numberOfCallsToApi = 0;

    private String cursor = "*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsDataShare = NewsDataShare.getSharedInstance();

        parentRecyclerView = findViewById(R.id.parent_recycler_view);
        noInternet = findViewById(R.id.main_activity_no_internet);
        seeOffline = findViewById(R.id.main_activity_see_article_offline);

        seeOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SavedStoryActivity.class));
            }
        });

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        parentRecyclerView.setLayoutManager(layoutManager);

        storyAdapter = new StoryAdapter(masterStoryList, globalContext);
        parentRecyclerView.setAdapter(storyAdapter);

        parentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(isLoading)
                    return;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    //End of list
                    getResponsesFromNewsApi(globalContext);

                }
            }
        });

        getResponsesFromNewsApi(globalContext);

    }

/*
    private void setRecyclerView(MainPojoResponse mainPojoResponse, final Context context){

    }
*/


    @Override
    public void getResponsesFromNewsApi(final Context context){

//        String URL = "https://api.newsapi.aylien.com/api/v1/stories";
        String URL = null;

        if(cursor.equals("*")){
            URL = "https://api.newsapi.aylien.com/api/v1/stories?text=\"insurance\"&language[]=en&published_at.start=NOW-30DAYS&published_at.end=NOW&entities.title.text[]=\"insurance\"&per_page=10";
        }else {
            try {

                cursor = URLEncoder.encode(cursor, "UTF-8");
                URL = "https://api.newsapi.aylien.com/api/v1/stories?text=\"insurance\"&language[]=en&published_at.start=NOW-30DAYS&published_at.end=NOW&entities.title.text[]=\"insurance\"&per_page=10" +
                        "&cursor=" + cursor;
                Log.v(TAG,"URL is " + URL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d(TAG,"Issue while encoding" + e.getMessage());
            }
        }


        RequestQueue queue = Volley.newRequestQueue(context);
        isLoading = true;
        numberOfCallsToApi++;
        final int finalNumberOfCallsToApi = numberOfCallsToApi;
        StringRequest sr = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("alien", "response from server for retrieving=" + response);
                Log.d(TAG,"#" + finalNumberOfCallsToApi + "call to API");

                isLoading = false;
                internetAvailableFlag = true;
                try {
                    noInternet.setVisibility(View.GONE);
                    seeOffline.setVisibility(View.GONE);
                    parentRecyclerView.setVisibility(View.VISIBLE);

                    JSONObject obj = new JSONObject(response);
                    Gson gson = new Gson();

                    MainPojoResponse mainPojoResponse = gson.fromJson(obj.toString(), MainPojoResponse.class);


                    masterStoryList.addAll(mainPojoResponse.getStories());
                    storyAdapter.notifyDataSetChanged();

                    newsDataShare.mainPojoResponse = mainPojoResponse;
                    //updating singleton object with COMPLETE data of stories
                    newsDataShare.mainPojoResponse.setStories(masterStoryList);
                    newsDataShare.mainPojoResponse.setNextPageCursor(mainPojoResponse.getNextPageCursor());


                    cursor = mainPojoResponse.getNextPageCursor();

                } catch (JSONException ex) {
                    Log.d(TAG, "Retrieve json error is=" + ex);

                } catch (NullPointerException ex) {
                    Log.d(TAG, "Retrieve Nullpointer error is=" + ex);
                } catch (Exception ex) {
                    Log.d(TAG, "Retrieve error is=" + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                internetAvailableFlag = false;
                noInternet.setVisibility(View.VISIBLE);
                seeOffline.setVisibility(View.VISIBLE);
                parentRecyclerView.setVisibility(View.GONE);

                Log.d(TAG, "response from server for retrieve=error=" + error);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.d(TAG, "response from server for retrieve=1=" + error);

                } else if (error instanceof AuthFailureError) {
                    Log.d(TAG, "response from server for retrieve=2" + error);

                } else if (error instanceof ServerError) {
                    Log.d(TAG, "response from server for retrieve=3" + error.getMessage());

                } else if (error instanceof NetworkError) {
                    Log.d(TAG, "response from server for retrieve=4" + error);

                } else if (error instanceof ParseError) {
                    Log.d(TAG, "response from server for retrieve=5" + error);

                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                /*Accept:application/json, text/xml
                Content-Type:application/x-www-form-urlencoded
                X-AYLIEN-NewsAPI-Application-ID:4c50fc3b
                X-AYLIEN-NewsAPI-Application-Key:c8ea3419e09f7bbc67e542dea1705a18
                */

                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json, text/xml");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("X-AYLIEN-NewsAPI-Application-ID", AYLIEN_APPLICATION_ID);
                params.put("X-AYLIEN-NewsAPI-Application-Key", AYLIEN_APPLICATION_KEY);
                return params;
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr.setShouldCache(false);
        queue.add(sr);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_saved_articles) {
            startActivity(new Intent(MainActivity.this, SavedStoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
