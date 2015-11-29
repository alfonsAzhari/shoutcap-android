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

    public SessionManager(Context context) {

        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String shoutId, String sessionId) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SHOUTID, shoutId);
        editor.putString(KEY_SESSIONID, sessionId);
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

        return user;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();

        Intent i = new Intent(mContext, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
