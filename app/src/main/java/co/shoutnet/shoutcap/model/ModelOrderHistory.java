package co.shoutnet.shoutcap.model;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class ModelOrderHistory {
    private String result;
    private ArrayList<Item> item;

    public ModelOrderHistory(String result, ArrayList<Item> item) {
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
        private String id_order;
        private String order_date;
        private String due_date;
        private int price;
        private String payment_status;
        private String production_status;
        @SerializedName("e-connote")
        private String e_connote;

        public String getId_order() {
            return id_order;
        }

        public void setId_order(String id_order) {
            this.id_order = id_order;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getDue_date() {
            return due_date;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getProduction_status() {
            return production_status;
        }

        public void setProduction_status(String production_status) {
            this.production_status = production_status;
        }

        public String getE_connote() {
            return e_connote;
        }

        public void setE_connote(String e_connote) {
            this.e_connote = e_connote;
        }
    }
}
