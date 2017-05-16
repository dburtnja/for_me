
package com.example.denys.androidticketfinder.Search.train_search.coach;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("places")
    @Expose
    private Places places;

    public Places getPlaces() {
        return places;
    }

    public void setPlaces(Places places) {
        this.places = places;
    }

}
