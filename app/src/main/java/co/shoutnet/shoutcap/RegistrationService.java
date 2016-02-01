package co.shoutnet.shoutcap;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

import co.shoutnet.shoutcap.utility.ConfigGCM;
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Henra SN on 9/15/2015.
 */
public class RegistrationService extends IntentService {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String TAG = "RegisterService";
    private static final String[] TOPIC = {"global"};

    public RegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
//            Log.i("senderID", String.valueOf(R.string.gcm_defaultSenderId));
//            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            String token = instanceID.getToken(ConfigGCM.GOOGLE_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            instanceID.deleteToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);

//            Log.i(TAG, "GCM Reg Token: " + token);

            sendRegistrationToServer(token);
            subscribeTopic(token);

            sharedPreferences.edit().putBoolean(ConfigGCM.SENT_TOKEN_TO_SERVER, true).apply();
        } catch (IOException e) {
//            Log.d(TAG, "Failed to complete token refresh", e);

            sharedPreferences.edit().putBoolean(ConfigGCM.SENT_TOKEN_TO_SERVER, false).apply();
        }

        Intent registrationComplate = new Intent(ConfigGCM.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplate);
    }

    private void subscribeTopic(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPIC) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

    private void sendRegistrationToServer(final String token) throws IOException {
        HashMap<String, String> user;
        SessionManager manager;

        manager = new SessionManager(getApplicationContext());
        user = manager.getUserDetails();

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("regId", token)
                .add("userId", user.get("shoutId"))
                .build();
        Request request = new Request.Builder()
                .url(ConfigGCM.SERVER_URL)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
//            Log.i(TAG, response.body().toString());
        }
    }
}
