package co.shoutnet.shoutcap;

import java.util.Date;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class ModelVoucher {
    private String VoucherCode;
    private String Discount;
    private String Expire;
    private String UseAtOrder;
    private boolean Status;

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

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
