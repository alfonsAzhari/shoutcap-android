package co.shoutnet.shoutcap.utility;

/**
 * Created by alfons on 19/11/15.
 */
public class ApiReferences {

    private static String URL = "https://api.shoutnet.co/";

    private static String MOD_LOGIN = "shoutid/login.php";
    private static String MOD_INBOX = "shoutid/get_inbox.php";

    public static String getUrlLogin() {
        return URL + MOD_LOGIN;
    }

    public static String getUrlGetInbox() {
        return URL + MOD_INBOX;
    }
}
