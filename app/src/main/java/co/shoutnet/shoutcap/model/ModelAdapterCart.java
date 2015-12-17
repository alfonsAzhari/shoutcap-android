package co.shoutnet.shoutcap.model;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class ModelAdapterCart {
    private int id;
    private String image;
    private String name; //text
    private int price;
    private long subTotal;
    private int qty;

    public ModelAdapterCart() {
    }

    public ModelAdapterCart(int id, String image, String name, int price, long subTotal, int qty) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.subTotal = subTotal;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(long subTotal) {
        this.subTotal = subTotal;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
