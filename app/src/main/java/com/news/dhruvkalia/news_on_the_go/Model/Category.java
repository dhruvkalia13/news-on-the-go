
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("confident")
    @Expose
    private Boolean confident;
    @SerializedName("links")
    @Expose
    private Links__ links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

    public Links__ getLinks() {
        return links;
    }

    public void setLinks(Links__ links) {
        this.links = links;
    }

}
