package com.links.links.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.links.links.R;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class GCMRegService extends IntentService {

    public static final String REGISTRATION_SUCCESS = "registrationSuccess";
    public static final String REGISTRATION_ERROR = "registrationError";
    public static final String TOKEN = "token";


    public GCMRegService() {
        super("GCMRegService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            registerGCM();
        }
    }

    private void registerGCM(){
        Intent registrationComplete = null;
        String token = null;
        try {
            InstanceID instanceid = InstanceID.getInstance(getApplicationContext());
            token = instanceid.getToken(getString(R.string.gcm_sender_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("token", token);
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra(TOKEN, token);

            Intent intent =  new Intent(this, NewUser.class);
            intent.putExtra("token", token);
            startService(intent);

        }catch (Exception e){
            Log.i("token", "failed");
            registrationComplete = new Intent(REGISTRATION_ERROR);

        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }


}
