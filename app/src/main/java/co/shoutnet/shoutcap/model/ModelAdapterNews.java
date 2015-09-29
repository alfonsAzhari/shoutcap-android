package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class ModelAdapterNews {

    private String title;
    private String date;
    private int photo;

    public ModelAdapterNews(String title, String date, int photo) {
        this.title = title;
        this.date = date;
        this.photo = photo;
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

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

}

