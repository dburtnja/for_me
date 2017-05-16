
package com.example.denys.androidticketfinder.Search.train_search.search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("num")
    @Expose
    private String num;
    @SerializedName("model")
    @Expose
    private Integer model;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("travel_time")
    @Expose
    private String travelTime;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("till")
    @Expose
    private Till till;
    @SerializedName("types")
    @Expose
    private List<Type> types = null;
    @SerializedName("allow_stud")
    @Expose
    private Integer allowStud;
    @SerializedName("allow_transportation")
    @Expose
    private Integer allowTransportation;
    @SerializedName("allow_booking")
    @Expose
    private Integer allowBooking;
    @SerializedName("reserve_error")
    @Expose
    private String reserveError;
    @SerializedName("transformer")
    @Expose
    private Boolean transformer;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Till getTill() {
        return till;
    }

    public void setTill(Till till) {
        this.till = till;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Integer getAllowStud() {
        return allowStud;
    }

    public void setAllowStud(Integer allowStud) {
        this.allowStud = allowStud;
    }

    public Integer getAllowTransportation() {
        return allowTransportation;
    }

    public void setAllowTransportation(Integer allowTransportation) {
        this.allowTransportation = allowTransportation;
    }

    public Integer getAllowBooking() {
        return allowBooking;
    }

    public void setAllowBooking(Integer allowBooking) {
        this.allowBooking = allowBooking;
    }

    public String getReserveError() {
        return reserveError;
    }

    public void setReserveError(String reserveError) {
        this.reserveError = reserveError;
    }

    public Boolean getTransformer() {
        return transformer;
    }

    public void setTransformer(Boolean transformer) {
        this.transformer = transformer;
    }

}
