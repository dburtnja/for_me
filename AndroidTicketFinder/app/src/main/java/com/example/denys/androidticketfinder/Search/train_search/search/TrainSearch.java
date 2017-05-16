
package com.example.denys.androidticketfinder.Search.train_search.search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainSearch {

    @SerializedName("value")
    @Expose
    private List<Value> value = null;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("captcha")
    @Expose
    private Object captcha;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Object captcha) {
        this.captcha = captcha;
    }

}
