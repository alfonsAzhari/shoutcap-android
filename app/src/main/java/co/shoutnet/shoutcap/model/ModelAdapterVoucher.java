package co.shoutnet.shoutcap.model;

import android.util.Log;

/**
 * Created by Adam MB on 1/9/2016.
 */
public class ModelAdapterVoucher {
    private String VoucherCode;
    private String Discount;
    private String DiscountTo;
    private String Expire;
    private String UseAtOrder;
    private String Status;

    public ModelAdapterVoucher(String voucherCode, String discount, String discountTo, String expire, String useAtOrder, String status) {
        VoucherCode = voucherCode;
        Discount = discount;
        DiscountTo = discountTo;
        Expire = expire;
        UseAtOrder = useAtOrder;
        Status = status;
    }

    public String getVoucherCode() {
        Log.i("get", "get");
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
