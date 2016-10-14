package com.links.links.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by mahmoud on 9/15/2016.
 */
public class LinksSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SyncAdapter mLinksSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (mLinksSyncAdapter == null) {
                mLinksSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mLinksSyncAdapter.getSyncAdapterBinder();
    }
}
