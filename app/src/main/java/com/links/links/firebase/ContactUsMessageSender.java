package com.links.links.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class ContactUsMessageSender extends IntentService {

    public static String LOG_TAG = ContactUsMessageSender.class.getSimpleName();

    public static final String CLIENT_NAME = "ClientName";
    public static final String EMAIL = "email";
    public static final String SERVICE = "service";
    public static final String MESSAGE = "message";
    private static final String TAG = "ContactUsMessageSender";

    private boolean notDone;

    private FirebaseDatabase database;

    public ContactUsMessageSender() {
        super("ContactUsMessageSender");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        if (intent != null) {
            database = FirebaseDatabase.getInstance();
            DatabaseReference msgRef = database.getReference().child("MSGs").getRef();

            String name = intent.getStringExtra(CLIENT_NAME);
            String Email = intent.getStringExtra(EMAIL);
            String Service = intent.getStringExtra(SERVICE);
            String Messsage = intent.getStringExtra(MESSAGE);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date dateobj = new Date();
            String date = dateFormat.format(dateobj);


            String newKey = msgRef.push().getKey();
            Log.i(LOG_TAG, newKey);

            Map<String, Object> values = new HashMap<>();

            values.put("user_name", name);
            values.put("user_email", Email);
            values.put("service", Service);
            values.put("msg", Messsage);
            values.put("date", date);

            Map<String, Object> fina = new HashMap<>();
            fina.put("/MSGs/" + newKey, values);
            database.getReference().updateChildren(fina);
        }
    }


}
