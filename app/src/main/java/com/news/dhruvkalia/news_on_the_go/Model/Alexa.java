
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alexa extends RealmObject{

    @PrimaryKey
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("fetched_at")
    @Expose
    private String fetchedAt;
    @SerializedName("country")
    @Expose
    private String country;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(String fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
