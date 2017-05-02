
package objects.coaches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prices {

    @SerializedName("Б")
    @Expose
    private Integer B;

    public Integer getB() {
        return B;
    }

    public void setB(Integer B) {
        this.B = B;
    }

}
