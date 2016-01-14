package co.shoutnet.shoutcap.model;

import java.util.ArrayList;

/**
 * Created by Adam MB on 1/14/2016.
 */
public class ModelCaraBayar {
    private String result;
    private ArrayList<Item> item;

    public ModelCaraBayar(String result, ArrayList<Item> item) {
        this.result = result;
        this.item = item;
    }

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

    public class Item{
        private String id_cara_bayar;
        private String cara_bayar;

        public String getId_cara_bayar() {
            return id_cara_bayar;
        }

        public void setId_cara_bayar(String id_cara_bayar) {
            this.id_cara_bayar = id_cara_bayar;
        }

        public String getCara_bayar() {
            return cara_bayar;
        }

        public void setCara_bayar(String cara_bayar) {
            this.cara_bayar = cara_bayar;
        }
    }
}
