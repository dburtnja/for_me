
package objects.coach;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coach {

    @SerializedName("value")
    @Expose
    private Value value;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("captcha")
    @Expose
    private Object captcha;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
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
