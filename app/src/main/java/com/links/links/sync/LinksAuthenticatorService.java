package com.links.links.sync;

import android.content.Intent;
import android.os.IBinder;

import android.app.Service;

/**
 * Created by mahmoud on 9/15/2016.
 */
public class LinksAuthenticatorService extends Service {
    private LinksAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new LinksAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
