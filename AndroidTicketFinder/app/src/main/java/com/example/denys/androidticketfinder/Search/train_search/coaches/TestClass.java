
package com.example.denys.androidticketfinder.Search.train_search.coaches;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestClass {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("coach_type_id")
    @Expose
    private Integer coachTypeId;
    @SerializedName("coaches")
    @Expose
    private List<Coaches> coaches = null;
    @SerializedName("places_allowed")
    @Expose
    private Integer placesAllowed;
    @SerializedName("places_max")
    @Expose
    private Integer placesMax;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCoachTypeId() {
        return coachTypeId;
    }

    public void setCoachTypeId(Integer coachTypeId) {
        this.coachTypeId = coachTypeId;
    }

    public List<Coaches> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coaches> coaches) {
        this.coaches = coaches;
    }

    public Integer getPlacesAllowed() {
        return placesAllowed;
    }

    public void setPlacesAllowed(Integer placesAllowed) {
        this.placesAllowed = placesAllowed;
    }

    public Integer getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(Integer placesMax) {
        this.placesMax = placesMax;
    }

}
