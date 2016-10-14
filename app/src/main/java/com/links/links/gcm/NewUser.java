package com.links.links.gcm;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 *
 */
public class NewUser extends IntentService {

    public NewUser() {
        super("NewUser");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            String token = intent.getStringExtra("token");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("users").getRef();
            String key = ref.push().getKey();

            Map<String, Object> values = new HashMap<>();

            values.put("token", token);

            Map<String, Object> fina = new HashMap<>();
            fina.put("/MSGs/" + key, values);
            database.getReference().updateChildren(fina);

//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost("http://192.168.1.5/Authentication/users/new");

//            try {
//                //add data
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                nameValuePairs.add(new BasicNameValuePair("token", token));
//
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                //execute http post
//                HttpResponse response = httpclient.execute(httppost);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//                StringBuilder builder = new StringBuilder();
//                for (String line = null; (line = reader.readLine()) != null;) {
//                    builder.append(line).append("\n");
//                }
//
//                Log.i("NewUser", builder.toString());
//                Bundle data = new Bundle();
//                data.putString("data", builder.toString());
//            } catch (ClientProtocolException e) {
//                Log.e("NewUser", e.toString());
////                Toast.makeText(getApplicationContext(), "CONNECTION PROBLEM", Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                Log.e("NewUser", e.toString());
////                Toast.makeText(getApplicationContext(), "CONNECTION PROBLEM", Toast.LENGTH_SHORT).show();
//            }
        }
    }


}
