package bean;

import java.math.BigDecimal;

/**
 * Created by amber on 2018/7/31.
 */

public class AED {
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
