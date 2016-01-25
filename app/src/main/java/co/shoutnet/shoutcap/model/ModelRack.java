package co.shoutnet.shoutcap.model;

/**
 * Created by Henra SN on 12/9/2015.
 */
public class ModelRack {
    private String result;
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public class Item {
        private String id_rack;

        public String getId_rack() {
            return id_rack;
        }

        public void setId_rack(String id_rack) {
            this.id_rack = id_rack;
        }
    }
}
