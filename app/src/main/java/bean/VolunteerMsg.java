package bean;

/**
 * Created by amber on 2018/11/28.
 */

public class VolunteerMsg {
    private String UserID;
    private String cid;
    private String search;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
