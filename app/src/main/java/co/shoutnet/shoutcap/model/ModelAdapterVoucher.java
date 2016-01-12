package co.shoutnet.shoutcap.model;

import android.util.Log;

/**
 * Created by Adam MB on 1/9/2016.
 */
public class ModelAdapterVoucher {
    private String voucher_code;
    private String status;
    private String expire;
    private String discount;
    private String discount_to;
    private String id_order;

    public ModelAdapterVoucher(String voucher_code, String status, String expire, String discount, String discount_to, String id_order) {
        this.voucher_code = voucher_code;
        this.status = status;
        this.expire = expire;
        this.discount = discount;
        this.discount_to = discount_to;
        this.id_order = id_order;
    }

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
