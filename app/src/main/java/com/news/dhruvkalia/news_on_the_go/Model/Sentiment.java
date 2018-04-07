
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Sentiment {

    @SerializedName("title")
    @Expose
    private Title_ title;
    @SerializedName("body")
    @Expose
    private Body_ body;

    public Title_ getTitle() {
        return title;
    }

    public void setTitle(Title_ title) {
        this.title = title;
    }

    public Body_ getBody() {
        return body;
    }

    public void setBody(Body_ body) {
        this.body = body;
    }

}
