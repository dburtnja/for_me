
package objects.coaches;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coaches {

    @SerializedName("num")
    @Expose
    private Integer num;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("allow_bonus")
    @Expose
    private Boolean allowBonus;
    @SerializedName("places_cnt")
    @Expose
    private Integer placesCnt;
    @SerializedName("has_bedding")
    @Expose
    private Boolean hasBedding;
    @SerializedName("reserve_price")
    @Expose
    private Integer reservePrice;
    @SerializedName("services")
    @Expose
    private List<String> services = null;
    @SerializedName("prices")
    @Expose
    private Prices prices;
    @SerializedName("coach_type_id")
    @Expose
    private Integer coachTypeId;
    @SerializedName("scheme_id")
    @Expose
    private Integer schemeId;
    @SerializedName("coach_class")
    @Expose
    private String coachClass;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAllowBonus() {
        return allowBonus;
    }

    public void setAllowBonus(Boolean allowBonus) {
        this.allowBonus = allowBonus;
    }

    public Integer getPlacesCnt() {
        return placesCnt;
    }

    public void setPlacesCnt(Integer placesCnt) {
        this.placesCnt = placesCnt;
    }

    public Boolean getHasBedding() {
        return hasBedding;
    }

    public void setHasBedding(Boolean hasBedding) {
        this.hasBedding = hasBedding;
    }

    public Integer getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(Integer reservePrice) {
        this.reservePrice = reservePrice;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Integer getCoachTypeId() {
        return coachTypeId;
    }

    public void setCoachTypeId(Integer coachTypeId) {
        this.coachTypeId = coachTypeId;
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

    public String getCoachClass() {
        return coachClass;
    }

    public void setCoachClass(String coachClass) {
        this.coachClass = coachClass;
    }
}
