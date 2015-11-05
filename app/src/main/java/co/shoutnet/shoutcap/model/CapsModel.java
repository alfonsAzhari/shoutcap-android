package co.shoutnet.shoutcap.model;

/**
 * Created by mikqi on 10/20/15.
 */
public class CapsModel {
    private String id;
    private String name;
    private String text;
    private int model;
    private int type;
    private int size;
    private String font;
    private String color;
    private int price;
    private String baseImage;

    public CapsModel() {
    }

    public CapsModel(String name, String text, int model, int type, int size, String font, String color, int price, String baseImage) {
        this.name = name;
        this.text = text;
        this.model = model;
        this.type = type;
        this.size = size;
        this.font = font;
        this.color = color;
        this.price = price;
        this.baseImage = baseImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }
}
