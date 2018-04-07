
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Source {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("links_in_count")
    @Expose
    private int linksInCount;
    @SerializedName("home_page_url")
    @Expose
    private String homePageUrl;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("locations")
    @Expose
    private List<Object> locations = null;
    @SerializedName("scopes")
    @Expose
    private List<Scope> scopes = null;
    @SerializedName("rankings")
    @Expose
    private Rankings rankings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLinksInCount() {
        return linksInCount;
    }

    public void setLinksInCount(int linksInCount) {
        this.linksInCount = linksInCount;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<Object> getLocations() {
        return locations;
    }

    public void setLocations(List<Object> locations) {
        this.locations = locations;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    public Rankings getRankings() {
        return rankings;
    }

    public void setRankings(Rankings rankings) {
        this.rankings = rankings;
    }

}
