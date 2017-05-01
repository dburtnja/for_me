
package objects.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Till {

    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("src_date")
    @Expose
    private String srcDate;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getSrcDate() {
        return srcDate;
    }

    public void setSrcDate(String srcDate) {
        this.srcDate = srcDate;
    }

}
