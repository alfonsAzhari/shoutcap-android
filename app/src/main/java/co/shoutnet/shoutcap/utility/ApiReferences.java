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
    private static String MOD_CHANGE_AVA = "shoutid/change_avatar.php";

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

    public static String getUrlChangeAvatar() {
        return URL + MOD_CHANGE_AVA;
    }
}
