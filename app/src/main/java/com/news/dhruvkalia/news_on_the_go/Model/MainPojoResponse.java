
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainPojoResponse {

    @SerializedName("stories")
    @Expose
    private List<Story> stories = null;
    /*@SerializedName("clusters")
    @Expose
    private List<Object> clusters = null;
    */
    @SerializedName("next_page_cursor")
    @Expose
    private String nextPageCursor;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
/*

    public List<Object> getClusters() {
        return clusters;
    }

    public void setClusters(List<Object> clusters) {
        this.clusters = clusters;
    }
*/

    public String getNextPageCursor() {
        return nextPageCursor;
    }

    public void setNextPageCursor(String nextPageCursor) {
        this.nextPageCursor = nextPageCursor;
    }

}
