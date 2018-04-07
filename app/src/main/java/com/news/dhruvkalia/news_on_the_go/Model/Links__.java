
package com.news.dhruvkalia.news_on_the_go.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Links__ extends RealmObject {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("parent")
    @Expose
    private String parent;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

}
