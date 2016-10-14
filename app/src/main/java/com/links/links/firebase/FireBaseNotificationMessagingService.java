package com.links.links.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.links.links.MainActivity;
import com.links.links.R;
import com.links.links.Utility;

/**
 * Created by mahmoud on 10/12/2016.
 */
public class FireBaseNotificationMessagingService extends FirebaseMessagingService {

    private static String TAG = FireBaseNotificationMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            String message = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();

            if (Utility.isEmptyOrNull(title)){
                title = "LINKS";
            }

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_links_app_icon)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSound(sound);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String notifications =  preferences.getString(getString(R.string.pref_app_notifications_key),getString(R.string.notifications_on));
            if (notifications.equals(getString(R.string.notifications_on))){
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
            }


        }

    }


}
