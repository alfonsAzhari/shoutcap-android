package co.shoutnet.shoutcap.model;

/**
 * Created by mikqi on 11/21/15.
 */
public class ModelCapModel {
    private int id;
    private String img_path;
    private int price;

    public ModelCapModel() {
    }

    public ModelCapModel(String img_path, int price, int id) {
        this.img_path = img_path;
        this.price = price;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
