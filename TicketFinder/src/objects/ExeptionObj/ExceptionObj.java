package objects.ExeptionObj;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import objects.search.Value;

import java.util.List;

/**
 * Created by Denys on 01.05.2017.
 */
public class ExceptionObj {
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("captcha")
    @Expose
    private Object captcha;
}
