package com.links.links.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.links.links.MainActivity;
import com.links.links.R;

/**
 * Created by mahmoud on 9/26/2016.
 */
public class GCMPushReceiverService extends GcmListenerService {
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        Log.i("GCMPushReceiverService", "message received");
        String message = bundle.getString("message");
        sendNotification(message);
    }
    private void sendNotification(String message){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode  =  0;
        PendingIntent pendingIntent =PendingIntent.getActivity(this,requestCode,intent, PendingIntent.FLAG_ONE_SHOT );

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentTitle("LINKS")
                .setContentIntent(pendingIntent)
                .setSound(sound);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notifications =  preferences.getString(getString(R.string.pref_app_notifications_key),getString(R.string.notifications_on));
        if (notifications.equals(getString(R.string.notifications_on))){
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify( 0, builder.build());

    }
}
