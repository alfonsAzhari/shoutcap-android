package co.shoutnet.shoutcap.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class ModelVoucher {
    private String result;
    private ArrayList<Item> item;

    public ModelVoucher(String result, ArrayList<Item> item) {
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

    public class Item {
        private String voucher_code;
        private String status;
        private String expire;
        private String discount;
        private String discount_to;
        private String id_order;

        public String getVoucher_code() {
            return voucher_code;
        }

        public void setVoucher_code(String voucher_code) {
            this.voucher_code = voucher_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_to() {
            return discount_to;
        }

        public void setDiscount_to(String discount_to) {
            this.discount_to = discount_to;
        }

        public String getId_order() {
            return id_order;
        }

        public void setId_order(String id_order) {
            this.id_order = id_order;
        }
    }
}
