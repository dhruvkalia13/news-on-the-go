
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Links extends RealmObject {

    @SerializedName("dbpedia")
    @Expose
    private String dbpedia;

    public String getDbpedia() {
        return dbpedia;
    }

    public void setDbpedia(String dbpedia) {
        this.dbpedia = dbpedia;
    }

}
