package co.shoutnet.shoutcap.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import co.shoutnet.shoutcap.SignActivity;

/**
 * Created by Alfons on 27/11/2015.
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
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_KEC = "kecamatan";
    public static final String KEY_PROVINCE = "province";
    public static final String KEY_POSTAL_CODE = "postalCode";
    public static final String KEY_DATE_BIRTH = "dateBirth";
    public static final String KEY_MINAT = "minat";
    public static final String KEY_WORK_STATUS = "workStatus";

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

    public void addProfile(String name, String email, String phone, String gender, String address, String kec, String city, String province, String postalCode, String dateBirth, String minat, String workStatus) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_KEC, kec);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_PROVINCE, province);
        editor.putString(KEY_POSTAL_CODE, postalCode);
        editor.putString(KEY_DATE_BIRTH, dateBirth);
        editor.putString(KEY_MINAT, minat);
        editor.putString(KEY_WORK_STATUS, workStatus);

        editor.commit();
    }

    public void updateAvatar(String urlAva) {
        editor.putString(KEY_URL_AVATAR, urlAva);

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
        user.put(KEY_PICTO_QUOTA, pref.getString(KEY_PICTO_QUOTA, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_KEC, pref.getString(KEY_KEC, null));
        user.put(KEY_CITY, pref.getString(KEY_CITY, null));
        user.put(KEY_PROVINCE, pref.getString(KEY_PROVINCE, null));
        user.put(KEY_POSTAL_CODE, pref.getString(KEY_POSTAL_CODE, null));
        user.put(KEY_DATE_BIRTH, pref.getString(KEY_DATE_BIRTH, null));
        user.put(KEY_MINAT, pref.getString(KEY_MINAT, null));
        user.put(KEY_WORK_STATUS, pref.getString(KEY_WORK_STATUS, null));

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
