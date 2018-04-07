
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Summary{

    @PrimaryKey
    @SerializedName("sentences")
    @Expose
    private List<String> sentences = null;

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

}
