package co.shoutnet.shoutcap.model;

/**
 * Created by mikqi on 10/20/15.
 */
public class CapsModel {
    private String id;
    private String name;
    private String text;
    private int model;
    private String size;
    private String font;
    private int color;
    private int fontsize;
    private int line;
    private int price;
    private String baseImage;
    private String status;

    public CapsModel() {
    }

    public CapsModel(String name, String text, int model, String size, String font, int color, int fontsize, int line, int price, String baseImage, String status) {
        this.name = name;
        this.text = text;
        this.model = model;
        this.size = size;
        this.font = font;
        this.color = color;
        this.fontsize = fontsize;
        this.line = line;
        this.price = price;
        this.baseImage = baseImage;
        this.status = status;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
