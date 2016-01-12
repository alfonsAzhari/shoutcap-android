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
        private String VoucherCode;
        private String Discount;
        private String DiscountTo;
        private String Expire;
        private String UseAtOrder;
        private String Status;

        public String getVoucherCode() {
            return VoucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            VoucherCode = voucherCode;
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String discount) {
            Discount = discount;
        }

        public String getDiscountTo() {
            return DiscountTo;
        }

        public void setDiscountTo(String discountTo) {
            DiscountTo = discountTo;
        }

        public String getExpire() {
            return Expire;
        }

        public void setExpire(String expire) {
            Expire = expire;
        }

        public String getUseAtOrder() {
            return UseAtOrder;
        }

        public void setUseAtOrder(String useAtOrder) {
            UseAtOrder = useAtOrder;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
    }
}
