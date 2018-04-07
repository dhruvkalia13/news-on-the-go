
package com.news.dhruvkalia.news_on_the_go.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Rankings{


    @SerializedName("alexa")
    @Expose
    private List<Alexa> alexa = null;

    public List<Alexa> getAlexa() {
        return alexa;
    }

    public void setAlexa(List<Alexa> alexa) {
        this.alexa = alexa;
    }

}
