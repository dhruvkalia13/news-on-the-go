
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Body{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("links")
    @Expose
    private Links_ links;
    @SerializedName("indices")
    @Expose
    private List<List<Integer>> indices = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Links_ getLinks() {
        return links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }

    public List<List<Integer>> getIndices() {
        return indices;
    }

    public void setIndices(List<List<Integer>> indices) {
        this.indices = indices;
    }

}
