package co.shoutnet.shoutcap.model;

/**
 * Created by Henra SN on 12/13/2015.
 */
public class ItemCartModel {
    private String name; //text
    private String size;
    private String image;
    private int price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
