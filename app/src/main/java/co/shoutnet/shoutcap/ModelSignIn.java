package co.shoutnet.shoutcap;

/**
 * Created by Adam MB on 1/7/2016.
 */
public class ModelSignIn {
    private String result;
    @SerializedName("shoutid")
    private String shoutId;
    private String point;
    private String coin;
    private String prevelege;
    @SerializedName("url_avatar")
    private String urlAvatar;
    @SerializedName("shoutcap_quota")
    private String shoutcapQuota;
    @SerializedName("screamshirt_quota")
    private String screamShirtQuota;
    @SerializedName("pictocap_quota")
    private String pictocapQuota;
    @SerializedName("sessionid")
    private String sessionId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getShoutId() {
        return shoutId;
    }

    public void setShoutId(String shoutId) {
        this.shoutId = shoutId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getPrevelege() {
        return prevelege;
    }

    public void setPrevelege(String prevelege) {
        this.prevelege = prevelege;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getShoutcapQuota() {
        return shoutcapQuota;
    }

    public void setShoutcapQuota(String shoutcapQuota) {
        this.shoutcapQuota = shoutcapQuota;
    }

    public String getScreamShirtQuota() {
        return screamShirtQuota;
    }

    public void setScreamShirtQuota(String screamShirtQuota) {
        this.screamShirtQuota = screamShirtQuota;
    }

    public String getPictocapQuota() {
        return pictocapQuota;
    }

    public void setPictocapQuota(String pictocapQuota) {
        this.pictocapQuota = pictocapQuota;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
