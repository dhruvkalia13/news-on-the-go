
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Story extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    /*@SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("source")
    @Expose
    private Source source;
    */@SerializedName("author")
    @Expose
    private Author author;
    /*@SerializedName("entities")
    @Expose
    private Entities entities;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords = null;
    @SerializedName("hashtags")
    @Expose
    private List<String> hashtags = null;
    @SerializedName("characters_count")
    @Expose
    private int charactersCount;
    @SerializedName("words_count")
    @Expose
    private int wordsCount;
    @SerializedName("sentences_count")
    @Expose
    private int sentencesCount;
    @SerializedName("paragraphs_count")
    @Expose
    private int paragraphsCount;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("social_shares_count")
    @Expose
    private SocialSharesCount socialSharesCount;
    */@SerializedName("media")
    @Expose
    private RealmList<Medium> media = null;
    /*@SerializedName("sentiment")
    @Expose
    private Sentiment sentiment;
    @SerializedName("language")
    @Expose
    private String language;
    */@SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("links")
    @Expose
    private Links___ links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    /*

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public int getCharactersCount() {
        return charactersCount;
    }

    public void setCharactersCount(int charactersCount) {
        this.charactersCount = charactersCount;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getSentencesCount() {
        return sentencesCount;
    }

    public void setSentencesCount(int sentencesCount) {
        this.sentencesCount = sentencesCount;
    }

    public int getParagraphsCount() {
        return paragraphsCount;
    }

    public void setParagraphsCount(int paragraphsCount) {
        this.paragraphsCount = paragraphsCount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public SocialSharesCount getSocialSharesCount() {
        return socialSharesCount;
    }

    public void setSocialSharesCount(SocialSharesCount socialSharesCount) {
        this.socialSharesCount = socialSharesCount;
    }
*/

    public RealmList<Medium> getMedia() {
        return media;
    }

    public void setMedia(RealmList<Medium> media) {
        this.media = media;
    }

/*
    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
*/

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Links___ getLinks() {
        return links;
    }

    public void setLinks(Links___ links) {
        this.links = links;
    }

}
