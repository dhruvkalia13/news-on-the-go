
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SocialSharesCount{

    @SerializedName("facebook")
    @Expose
    private List<Object> facebook = null;
    @SerializedName("google_plus")
    @Expose
    private List<Object> googlePlus = null;
    @SerializedName("linkedin")
    @Expose
    private List<Object> linkedin = null;
    @SerializedName("reddit")
    @Expose
    private List<Object> reddit = null;

    public List<Object> getFacebook() {
        return facebook;
    }

    public void setFacebook(List<Object> facebook) {
        this.facebook = facebook;
    }

    public List<Object> getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(List<Object> googlePlus) {
        this.googlePlus = googlePlus;
    }

    public List<Object> getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(List<Object> linkedin) {
        this.linkedin = linkedin;
    }

    public List<Object> getReddit() {
        return reddit;
    }

    public void setReddit(List<Object> reddit) {
        this.reddit = reddit;
    }

}
