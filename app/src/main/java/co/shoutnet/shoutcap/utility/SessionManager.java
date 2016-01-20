package co.shoutnet.shoutcap.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import co.shoutnet.shoutcap.MainActivity;
import co.shoutnet.shoutcap.SignActivity;

/**
 * Created by CodeLabs on 27/11/2015.
 */
public class SessionManager {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context mContext;

    private static final String PREF_NAME = "ShoutcapPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_SHOUTID = "shoutId";
    public static final String KEY_SESSIONID = "sessionId";
    public static final String KEY_POINT = "point";
    public static final String KEY_COIN = "coin";
    public static final String KEY_URL_AVATAR = "urlAvatar";
    public static final String KEY_SHOUT_QUOTA = "shoutCapQuota";
    public static final String KEY_SCREAM_QUOTA = "screamShirtQuota";
    public static final String KEY_PICTO_QUOTA = "pictoCapQuota";

    public SessionManager(Context context) {

        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String shoutId, String sessionId, String point, String coin, String urlAva, String shoutQuota, String screamQuota, String pictoQuota) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SHOUTID, shoutId);
        editor.putString(KEY_SESSIONID, sessionId);
        editor.putString(KEY_POINT, point);
        editor.putString(KEY_COIN, coin);
        editor.putString(KEY_URL_AVATAR, urlAva);
        editor.putString(KEY_SHOUT_QUOTA, shoutQuota);
        editor.putString(KEY_SCREAM_QUOTA, screamQuota);
        editor.putString(KEY_PICTO_QUOTA, pictoQuota);
        editor.commit();
    }

    public void checkLogin() {

        if (!this.isLoggedIn()) {
            Intent i = new Intent(mContext, SignActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_SHOUTID, pref.getString(KEY_SHOUTID, null));
        user.put(KEY_SESSIONID, pref.getString(KEY_SESSIONID, null));
        user.put(KEY_POINT, pref.getString(KEY_POINT, null));
        user.put(KEY_COIN, pref.getString(KEY_COIN, null));
        user.put(KEY_URL_AVATAR, pref.getString(KEY_URL_AVATAR, null));
        user.put(KEY_SHOUT_QUOTA, pref.getString(KEY_SHOUT_QUOTA, null));
        user.put(KEY_SCREAM_QUOTA, pref.getString(KEY_SCREAM_QUOTA, null));
        user.put(KEY_PICTO_QUOTA, pref.getString(KEY_SCREAM_QUOTA, null));

        return user;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();

        Intent i = new Intent(mContext, SignActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
