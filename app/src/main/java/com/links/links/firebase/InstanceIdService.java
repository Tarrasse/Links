package com.links.links.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.links.links.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahmoud on 10/12/2016.
 */
public class InstanceIdService  extends FirebaseInstanceIdService {

    public static String TAG = InstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Utility.uploadRegistrationToServer(refreshedToken);
    }



}
