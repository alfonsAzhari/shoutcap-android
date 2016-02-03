package co.shoutnet.shoutcap.utility;

/**
 * Created by alfons on 19/11/15.
 */
public class ApiReferences {

    private static String URL = "https://api.shoutnet.co/";

    private static String MOD_LOGIN = "shoutid/login.php";
    private static String MOD_PROFILE = "shoutid/get_profile.php";
    private static String MOD_INBOX = "shoutid/get_inbox.php";
    private static String MOD_INBOX_DETAIL = "shoutid/get_inbox_detail.php";
    private static String MOD_REWARD_HISTORY = "shoutid/get_history_reward.php";
    private static String MOD_CHANGE_AVA = "shoutid/change_avatar.php";
    private static String MOD_CHANGE_PASS = "shoutid/change_password.php";
    private static String MOD_UPDATE_PROFILE = "shoutid/update_profile.php";

    private static String MOD_GET_PROVINCE = "shoutid/get_provinsi.php";
    private static String MOD_GET_CITY = "shoutid/get_kota.php";
    private static String MOD_GET_KEC = "shoutid/get_kecamatan.php";

    public static String getUrlLogin() {
        return URL + MOD_LOGIN;
    }

    public static String getUrlProfile() {
        return URL + MOD_PROFILE;
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

    public static String getUrlChangePass() {
        return URL + MOD_CHANGE_PASS;
    }

    public static String getUrlUpdateProfile() {
        return URL + MOD_UPDATE_PROFILE;
    }

    public static String getUrlGetProvince() {
        return URL + MOD_GET_PROVINCE;
    }

    public static String getUrlGetCity() {
        return URL + MOD_GET_CITY;
    }

    public static String getUrlGetKec() {
        return URL + MOD_GET_KEC;
    }
}
