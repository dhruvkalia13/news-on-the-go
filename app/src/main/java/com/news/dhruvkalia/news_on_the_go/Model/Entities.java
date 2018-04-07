
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Entities{

    @SerializedName("title")
    @Expose
    private List<Title> title = null;
    @SerializedName("body")
    @Expose
    private List<Body> body = null;

    public List<Title> getTitle() {
        return title;
    }

    public void setTitle(List<Title> title) {
        this.title = title;
    }

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

}
