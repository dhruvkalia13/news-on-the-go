package com.news.dhruvkalia.news_on_the_go;

import android.content.Context;
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
import com.news.dhruvkalia.news_on_the_go.Model.Article;
import com.news.dhruvkalia.news_on_the_go.Model.MainPojoResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NextPageListener{

    RecyclerView parentRecyclerView;
    List<Article> masterArticleList = new ArrayList<Article>();
    private String TAG = "MainActivity.java";
    private boolean isLoading = false;
    private int pageNumber = 1;
    private LinearLayoutManager layoutManager;
    private Context globalContext = this;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentRecyclerView = findViewById(R.id.parent_recycler_view);

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

        articleAdapter = new ArticleAdapter(masterArticleList, globalContext);
        parentRecyclerView.setAdapter(articleAdapter);

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
                    getResponsesFromNewsApi(++pageNumber, globalContext);

                }
            }
        });

        getResponsesFromNewsApi(pageNumber, globalContext);

    }

    private void setRecyclerView(MainPojoResponse mainPojoResponse, final Context context){

        if((mainPojoResponse.getStatus()).equals("ok")){
            masterArticleList.addAll(mainPojoResponse.getArticles());
            /*List<Article> mList = mainPojoResponse.getArticles();
            for (Article article: mList
                 ) {
                masterArticleList.add(article);
            }*/
            Log.v(TAG,"Now, masterArticleList contains " + masterArticleList.size() + " articles");

            articleAdapter.notifyDataSetChanged();
        } else{
            Toast.makeText(getApplicationContext(),"STATUS IS NOT OK", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getResponsesFromNewsApi(int pageNumber, final Context context){

        String URL =  "https://newsapi.org/v2/everything?q=insurance&page=" + pageNumber + "&apiKey=fbf9c6c019f842899dbdcddf469817ce";
        Log.v(TAG,"URL is " + URL);

        RequestQueue queue = Volley.newRequestQueue(context);
        isLoading = true;
        StringRequest sr = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "response from server for retrieving=" + response);

                isLoading = false;
                try {
                    JSONObject obj = new JSONObject(response);
                    Gson gson = new Gson();

                    MainPojoResponse mainPojoResponse = gson.fromJson(obj.toString(), MainPojoResponse.class);

                    setRecyclerView(mainPojoResponse, context);
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
        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
