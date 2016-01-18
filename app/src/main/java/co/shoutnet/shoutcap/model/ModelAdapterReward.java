package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 9/3/2015.
 */
public class ModelAdapterReward {

    private String title;
    private String date;
    private String coin;
    private String point;

    public ModelAdapterReward(String title, String date, String coin, String point) {
        this.title = title;
        this.date = date;
        this.coin = coin;
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
