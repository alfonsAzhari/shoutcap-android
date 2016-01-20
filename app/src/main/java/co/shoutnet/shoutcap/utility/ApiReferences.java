package co.shoutnet.shoutcap.utility;

/**
 * Created by alfons on 19/11/15.
 */
public class ApiReferences {

    private static String URL = "https://api.shoutnet.co/";

    private static String MOD_LOGIN = "shoutid/login.php";
    private static String MOD_INBOX = "shoutid/get_inbox.php";
    private static String MOD_INBOX_DETAIL = "shoutid/get_inbox_detail.php";
    private static String MOD_REWARD_HISTORY = "shoutid/get_history_reward.php";
    private static String MOD_VOUCHER = "shoutcap/get_voucher.php";
    private static String MOD_ORDER_HISTORY = "shoutcap/get_order_history.php";
    private static String MOD_INVOICE = "shoutcap/get_invoice.php";
    private static String MOD_CARA_BAYAR = "shoutcap/list_cara_bayar.php";
    private static String MOD_PAYMENT_CONFIRMATION = "shoutcap/konfirmasi_pembayaran.php";


    public static String getUrlLogin() {
        return URL + MOD_LOGIN;
    }

    public static String getUrlGetInbox() {
        return URL + MOD_INBOX;
    }

    public static String getUrlGetInboxDetail() {
        return URL + MOD_INBOX_DETAIL;
    }

    public static String getUrlGetRewardHistory() {
        return URL + MOD_REWARD_HISTORY;
    }

    public static String getUrlGetVoucher() { return URL + MOD_VOUCHER; }

    public static String getUrlOrderHistory() { return URL + MOD_ORDER_HISTORY; }

    public static String getInvoice() { return URL + MOD_INVOICE; }

    public static String getCaraBayar() { return URL + MOD_CARA_BAYAR; }

    public static String getPaymentConfirmation() { return URL + MOD_PAYMENT_CONFIRMATION; }
}
