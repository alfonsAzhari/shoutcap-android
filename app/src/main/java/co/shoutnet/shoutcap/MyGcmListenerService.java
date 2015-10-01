package co.shoutnet.shoutcap;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Codelabs on 9/22/2015.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListener";
    private boolean isTopic;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String point = data.getString("point");

        Log.v(TAG, message);
        Log.v(TAG, point);
        if (from.startsWith("/topics/")) {
            isTopic = true;
            sendNotification(message, isTopic, point);
        } else {
            isTopic = false;
            sendNotification(message, isTopic, point);
        }

    }

    private void sendNotification(String message, boolean isTopic, String point) {

        Intent intent=null;
        String title=null;
        PendingIntent pendingIntent;
        if (point.equals("inbox")) {
            intent = new Intent(this, ActivityInboxDetail.class);
            title = "ShoutCap - Inbox";
        }else if (point.equals("reward")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("point","reward");
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            title="ShoutCap - Reward";
        }else if (point.equals("promo")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("point","promo");
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            title="ShoutCap - Promo";
        }else{
            intent = new Intent(this, ActivityInboxDetail.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 333, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_card_giftcard_black_18dp)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(333, notificationBuilder.build());
    }
}
