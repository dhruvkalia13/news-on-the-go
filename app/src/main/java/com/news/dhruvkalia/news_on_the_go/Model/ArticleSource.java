package com.news.dhruvkalia.news_on_the_go.Model;

import android.os.Parcel;

import io.realm.RealmObject;

public class ArticleSource extends RealmObject {

    private String id;
    private String name;

    public ArticleSource(){

    }
    public ArticleSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    protected ArticleSource(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

}