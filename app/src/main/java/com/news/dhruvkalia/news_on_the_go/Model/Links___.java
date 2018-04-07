
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Links___ extends RealmObject {

    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("related_stories")
    @Expose
    private String relatedStories;
    @SerializedName("coverages")
    @Expose
    private String coverages;

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getRelatedStories() {
        return relatedStories;
    }

    public void setRelatedStories(String relatedStories) {
        this.relatedStories = relatedStories;
    }

    public String getCoverages() {
        return coverages;
    }

    public void setCoverages(String coverages) {
        this.coverages = coverages;
    }

}
