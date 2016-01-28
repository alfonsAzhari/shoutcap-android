package co.shoutnet.shoutcap.model;

import java.util.ArrayList;

/**
 * Created by codelabs on 1/28/16.
 */
public class ModelSyncRack {
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
        private String shout;
        private String image;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShout() {
            return shout;
        }

        public void setShout(String shout) {
            this.shout = shout;
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
