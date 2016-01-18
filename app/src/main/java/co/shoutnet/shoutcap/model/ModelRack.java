package co.shoutnet.shoutcap.model;

import java.util.ArrayList;

/**
 * Created by Henra SN on 12/9/2015.
 */
public class ModelRack {
    private String result;
    private ArrayList<Item> item;

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public class Item {
        private String id;
        private String shout;
        private String image;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getShout() {
            return shout;
        }

        public void setShout(String shout) {
            this.shout = shout;
        }
    }
}
