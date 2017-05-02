
package objects.coach;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("BBBB")
    @Expose
    private List<String> bBBB = null;

    public List<String> getBBBB() {
        return bBBB;
    }

    public void setBBBB(List<String> bBBB) {
        this.bBBB = bBBB;
    }

}
