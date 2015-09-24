package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 8/26/2015.
 */
public class ModelAdapterInbox {

    private String title;
    private String date;
    private String desc;

    public ModelAdapterInbox(String title, String date, String desc) {
        this.title = title;
        this.date = date;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
