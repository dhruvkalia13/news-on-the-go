
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Title extends RealmObject{

    @SerializedName("text")
    @Expose
    private String text;
/*
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("indices")
    @Expose
    private List<List<Integer>> indices = null;
*/

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

/*
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<List<Integer>> getIndices() {
        return indices;
    }

    public void setIndices(List<List<Integer>> indices) {
        this.indices = indices;
    }
*/

}
