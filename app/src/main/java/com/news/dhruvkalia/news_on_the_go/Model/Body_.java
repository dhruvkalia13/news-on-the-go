
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Body_ {

    @SerializedName("polarity")
    @Expose
    private String polarity;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
