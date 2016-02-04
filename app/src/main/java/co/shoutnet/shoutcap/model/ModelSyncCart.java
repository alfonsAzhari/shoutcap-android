package co.shoutnet.shoutcap.model;

import java.util.ArrayList;

/**
 * Created by codelabs on 2/3/16.
 */
public class ModelSyncCart {
    private String result;
    private ArrayList<Item> item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public class Item {
        private String id;
        private String code;
        private String color;
        private String visor;
        private String size;
        private String font_type;
        private String font_color;
        private String quantity;
        private String discount;
        private String discount_voucher;
        private String shout;
        private String image;
        private String price;

        public String getShout() {
            return shout;
        }

        public void setShout(String shout) {
            this.shout = shout;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getVisor() {
            return visor;
        }

        public void setVisor(String visor) {
            this.visor = visor;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getFont_type() {
            return font_type;
        }

        public void setFont_type(String font_type) {
            this.font_type = font_type;
        }

        public String getFont_color() {
            return font_color;
        }

        public void setFont_color(String font_color) {
            this.font_color = font_color;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_voucher() {
            return discount_voucher;
        }

        public void setDiscount_voucher(String discount_voucher) {
            this.discount_voucher = discount_voucher;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
